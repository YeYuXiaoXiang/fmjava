package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.SpecEntity;
import com.fmjava.core.pojo.specification.Specification;

import java.util.List;
import java.util.Map;

public interface SpecService {
    PageResult findPage(Integer page, Integer pageSize, Specification specification);


    void save(SpecEntity specEntity);

    SpecEntity findSpecWithId(Long id);

    void update(SpecEntity specEntity);

    void delete(Long[] idx);

    List<Map> selectOptionList();

}
