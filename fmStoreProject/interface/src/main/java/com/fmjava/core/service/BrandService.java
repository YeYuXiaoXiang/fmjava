package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.good.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    public List<Brand> findAllBrands();

    PageResult findPage(Integer page, Integer pageSize, Brand brand);

    void addBrand(Brand brand);

    Brand findById(Long id);

    void update(Brand brand);

    void delete(Long[] idx);

    List<Map> selectOptionList();

}
