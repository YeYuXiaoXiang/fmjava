package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.GoodsEntity;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.good.Goods;
import com.fmjava.core.service.GoodsService;
import com.fmjava.core.service.SolrManagerService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;
    @Reference
    private SolrManagerService solrManagerService;

    @RequestMapping("/add")
    public Result add(@RequestBody GoodsEntity goodsEntity) {
        try {
            //获取登录用户名
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            //设置这个商品添加的用户名
            goodsEntity.getGoods().setSellerId(name);
            goodsService.add(goodsEntity);
            return new Result(true, "添加成功!");
        } catch (Exception e) {
            return new Result(false, "添加失败!");
        }
    }

    @RequestMapping("/search")
    public PageResult search(Integer page , Integer pageSize, @RequestBody Goods goods) {
        //获取当前登录用户的用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(userName);
        PageResult result = goodsService.findPage(goods, page, pageSize);
        return result;
    }

    @RequestMapping("/findOne")
    public GoodsEntity findOne(Long id) {
        return goodsService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody GoodsEntity goodsEntity) {
        try {
            //获取登录用户名
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            String sellerId = goodsEntity.getGoods().getSellerId();
            if (!sellerId.equals(userName)){
                return new Result(true, "没有权限更新商品");
            }
            goodsService.update(goodsEntity);

            return new Result(true, "添加成功!");
        } catch (Exception e) {
            return new Result(false, "添加失败!");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            solrManagerService.deleteItemSolr(Arrays.asList(ids));
            return  new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, "删除失败!");
        }
    }

}