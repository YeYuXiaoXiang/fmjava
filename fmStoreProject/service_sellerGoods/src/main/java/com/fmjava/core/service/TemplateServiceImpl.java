package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.fmjava.core.dao.specification.SpecificationOptionDao;
import com.fmjava.core.dao.template.TypeTemplateDao;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.specification.SpecificationOption;
import com.fmjava.core.pojo.specification.SpecificationOptionQuery;
import com.fmjava.core.pojo.template.TypeTemplate;
import com.fmjava.core.pojo.template.TypeTemplateQuery;
import com.fmjava.utils.Constants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    private TypeTemplateDao templateDao;

    @Autowired
    private SpecificationOptionDao specificationOptionDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows) {

        if (!redisTemplate.hasKey(Constants.BRAND_LIST_REDIS) || !redisTemplate.hasKey(Constants.SPEC_LIST_REDIS)){
            List<TypeTemplate> typeTemplates = templateDao.selectByExample(null);

            for (TypeTemplate typeTemplate : typeTemplates) {
                String brandIds = typeTemplate.getBrandIds();
                List<Map> brandList = JSON.parseArray(brandIds, Map.class);
                redisTemplate.boundHashOps(Constants.BRAND_LIST_REDIS).put(typeTemplate.getId(), brandList);

                List<Map> bySpecWithID = this.findBySpecWithID(typeTemplate.getId());
                redisTemplate.boundHashOps(Constants.SPEC_LIST_REDIS).put(typeTemplate.getId(), bySpecWithID);


            }
        }


        PageHelper.startPage(page, rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = query.createCriteria();
        if (template != null) {
            if (template.getName() != null && !"".equals(template.getName())) {
                criteria.andNameLike("%"+template.getName()+"%");
            }
        }
        Page<TypeTemplate> templateList =
                (Page<TypeTemplate>)templateDao.selectByExample(query);
        return new PageResult(templateList.getTotal(), templateList.getResult());
    }

    @Override
    public void add(TypeTemplate template) {
        templateDao.insertSelective(template);
    }

    @Override
    public TypeTemplate findOne(Long id) {

        return templateDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> findBySpecWithID(Long id) {

        TypeTemplate template = templateDao.selectByPrimaryKey(id);
        if (template != null){
            String specIds = template.getSpecIds();
            List<Map> mapsList = JSON.parseArray(specIds, Map.class);

            for (Map map : mapsList) {

                Object specIDObj = map.get("id");
                Long specID = Long.parseLong(String.valueOf(specIDObj));

                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(specID);

                List<SpecificationOption> specificationOptions = specificationOptionDao.selectByExample(query);
                System.out.println(specificationOptions+"============");
                map.put("specificationOptions", specificationOptions);
            }
            return mapsList;
        }
        return null;
    }
}
