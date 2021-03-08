package com.fmjava.core.service;

import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.template.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TemplateService {
    public PageResult findPage(TypeTemplate template, Integer page, Integer pageSize);

    void add(TypeTemplate template);

    TypeTemplate findOne(Long id);

    List<Map> findBySpecWithID(Long id);
}
