package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.item.ItemCat;
import com.fmjava.core.service.ItemCatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        return list;
    }

    @RequestMapping("/findOneCategory")
    public ItemCat findOneCategory(Long id) {
        ItemCat one = catService.findOne(id);
        return one;

    }

    @RequestMapping("/findAll")
    public List<ItemCat> findAll() {
        return catService.findAll();
    }


}