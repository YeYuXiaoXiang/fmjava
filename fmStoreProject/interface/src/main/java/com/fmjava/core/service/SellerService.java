package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.seller.Seller;

public interface SellerService {
    public void add(Seller seller);

    PageResult findPage(Integer page, Integer pageSize, Seller seller);

    Seller findOne(String id);

    void updateStatus(String sellerId, String status);
}
