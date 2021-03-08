package com.fmjava.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.pojo.item.Item;
import com.fmjava.core.service.SearchService;
import com.fmjava.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.*;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.Highlighter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Map<String, Object> search(Map paramMap) {
        //设置高亮显示
        Map<String, Object> resMap = this.setHighlightQuery(paramMap);

        List cateList = this.findCategory(paramMap);
        resMap.put("categoryList", cateList);

        String categoryName = String.valueOf(paramMap.get("category"));
        if (categoryName != null && "".equals(categoryName)){
            Map brandAndSpecMap = this.findBrandAndSpecWithCategory(categoryName);
            resMap.putAll(brandAndSpecMap);
        }else{
            if (cateList.size() > 0){
                String cateName = (String)cateList.get(0);
                Map brandAndSpecMap = this.findBrandAndSpecWithCategory(categoryName);
                resMap.putAll(brandAndSpecMap);
            }
        }

        return resMap;

    }

    private Map findBrandAndSpecWithCategory(String categoryName){
        Long typeID = (Long)redisTemplate.boundHashOps(Constants.CATEGORY_LIST_REDIS).get(categoryName);
        if (typeID != null){
            List<Map> brandList = (List<Map>)redisTemplate.boundHashOps(Constants.BRAND_LIST_REDIS).get(typeID);
            List<Map> specList = (List<Map>)redisTemplate.boundHashOps(Constants.SPEC_LIST_REDIS).get(typeID);

            Map map = new HashMap<>();
            map.put("brandList",brandList);
            map.put("specList",specList);
            return map;

        }
        return null;
    }



    private Map<String, Object> setHighlightQuery(Map paramMap){
        String keywords = String.valueOf(paramMap.get("keywords"));
        Integer pageNo = Integer.parseInt(String.valueOf(paramMap.get("pageNo")));
        Integer pageSize = Integer.parseInt(String.valueOf(paramMap.get("pageSize")));

        String category = String.valueOf(paramMap.get("category"));
        String brand = String.valueOf(paramMap.get("brand"));
        String spec = String.valueOf(paramMap.get("spec"));
        String price = String.valueOf(paramMap.get("price"));

        SimpleHighlightQuery query = new SimpleHighlightQuery();

        if (price != null && !"".equals(price)) {
            String[] split = price.split("-");
            if (split != null && split.length == 2) {
                if (!"0".equals(split[0])) {
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(split[0]);
                    filterQuery.addCriteria(filterCriteria);
                    query.addFilterQuery(filterQuery);
                }
                if (!"*".equals(split[1])) {
                    FilterQuery filterQuery = new SimpleFilterQuery();
                    Criteria filterCriteria = new Criteria("item_price").lessThanEqual(split[1]);
                    filterQuery.addCriteria(filterCriteria);
                    query.addFilterQuery(filterQuery);
                }
            }
        }

        HighlightOptions highlightOptions = new HighlightOptions();
        highlightOptions.addField("item_title");
        highlightOptions.setSimplePrefix("<em style=\"color:red\">");
        highlightOptions.setSimplePostfix("</em>");
        query.setHighlightOptions(highlightOptions);

        Criteria criteria = new Criteria("item_keywords").is(keywords);
        query.addCriteria(criteria);

        if (pageNo == null || pageNo <= 0){
            pageNo = 1;
        }
        Integer start = (pageNo - 1)*pageSize;
        query.setOffset(start);
        query.setRows(pageSize);

        HighlightPage<Item> items = solrTemplate.queryForHighlightPage(query, Item.class);

        ArrayList<Item> itemArrayList = new ArrayList<>();
        List<HighlightEntry<Item>> highlighted = items.getHighlighted();
        for (HighlightEntry<Item> itemHighlightEntry : highlighted) {
            Item entity = itemHighlightEntry.getEntity();
            List<HighlightEntry.Highlight> highlights = itemHighlightEntry.getHighlights();
            if (highlights != null && highlights.size() > 0){
                List<String> snipplets = highlights.get(0).getSnipplets();
                entity.setTitle(snipplets.get(0));
            }
            itemArrayList.add(entity);
        }

        HashMap<String, Object> resMap = new HashMap<>();
        resMap.put("rows", itemArrayList);
        resMap.put("total", items.getTotalElements());
        resMap.put("totalPages", items.getTotalPages());
        return resMap;
    }

    private List findCategory(Map paramMap){
        String keywords = String.valueOf(paramMap.get("keywords"));
        SimpleQuery query = new SimpleQuery();
        Criteria criteria = new Criteria("item_keywords").is(keywords);
        query.addCriteria(criteria);

        ArrayList<String> categoryList = new ArrayList<>();

        GroupOptions groupOptions = new GroupOptions();
        groupOptions.addGroupByField("item_category");
        query.setGroupOptions(groupOptions);

        GroupPage<Item> items = solrTemplate.queryForGroupPage(query, Item.class);
        GroupResult<Item> item_category = items.getGroupResult("item_category");
        Page<GroupEntry<Item>> groupEntries = item_category.getGroupEntries();
        for (GroupEntry<Item> groupEntry : groupEntries) {
            String groupValue = groupEntry.getGroupValue();
            categoryList.add(groupValue);
        }

        return categoryList;
    }

}

