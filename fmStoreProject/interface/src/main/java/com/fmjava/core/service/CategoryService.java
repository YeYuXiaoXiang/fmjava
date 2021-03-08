package com.fmjava.core.service;

import com.fmjava.core.pojo.ad.ContentCategory;
import com.fmjava.core.pojo.entity.PageResult;

import java.util.List;

public interface CategoryService {
    public PageResult findPage(ContentCategory category, Integer page, Integer rows);

    void add(ContentCategory category);

    ContentCategory findOne(Long id);

    void update(ContentCategory category);

    void delete(Long[] ids);

    List<ContentCategory> findAll();
}
