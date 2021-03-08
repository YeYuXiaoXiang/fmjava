package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/itemsearch")
public class SearchController {
    @Reference
    private SearchService searchService;

    @RequestMapping("/search")
    public Map<String, Object> search(@RequestBody Map paramMap) {
        Map<String, Object> resultMap = searchService.search(paramMap);
        System.out.println(resultMap);
        return resultMap;
    }
}