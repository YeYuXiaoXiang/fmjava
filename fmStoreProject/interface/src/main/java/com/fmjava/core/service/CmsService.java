package com.fmjava.core.service;

import java.util.Map;

public interface CmsService {
    /**
     * 根据商品ID和数据生成对应的静态页面
     * @param goodsId  商品ID
     * @param rootMap  商品数据
     * @throws Exception
     */
    public void createStaticPage(Long goodsId) throws Exception;
//    public void createStaticPage(Long goodsId, Map<String, Object> rootMap) throws Exception;

    /**
     * 根据商品ID获取商品数据
     * @param goodsId
     * @return
     */
//    public Map<String, Object> findGoodsData(Long goodsId);
}
