package com.fmjava.core.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.fmjava.core.dao.item.ItemCatDao;
import com.fmjava.core.pojo.item.ItemCat;
import com.fmjava.core.pojo.item.ItemCatQuery;
import com.fmjava.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao catDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {

        if (!redisTemplate.hasKey(Constants.CATEGORY_LIST_REDIS)){
            List<ItemCat> itemCatsAll = catDao.selectByExample(null);
            for (ItemCat itemCat : itemCatsAll) {
                redisTemplate.boundHashOps(Constants.CATEGORY_LIST_REDIS).put(itemCat.getName(),itemCat.getTypeId());

            }
        }


        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = catDao.selectByExample(query);
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        System.out.println(catDao.selectByPrimaryKey(id));

        return catDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {

        return catDao.selectByExample(null);
    }
}
