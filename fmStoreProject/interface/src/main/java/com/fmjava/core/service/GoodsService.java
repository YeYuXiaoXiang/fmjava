package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.GoodsEntity;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.good.Goods;
import com.fmjava.core.pojo.item.Item;

import java.util.List;

public interface GoodsService {
    public void add(GoodsEntity goodsEntity);

    PageResult findPage(Goods goods, Integer page, Integer pageSize);

    GoodsEntity findOne(Long id);

    void update(GoodsEntity goodsEntity);

    void delete(Long[] ids);

    void updateStatus(Long[] ids, String status);

    public List<Item> findItemByGoodsIdAndStatus(Long[]ids, String status);

}
