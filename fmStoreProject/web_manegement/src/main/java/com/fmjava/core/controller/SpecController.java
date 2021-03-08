package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.entity.SpecEntity;
import com.fmjava.core.pojo.specification.Specification;
import com.fmjava.core.service.SpecService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spec")
public class SpecController {

    @Reference
    private SpecService specService;

    @RequestMapping("/search")
    public PageResult search(Integer page, Integer pageSize, @RequestBody Specification specification){
        PageResult result = specService.findPage(page, pageSize, specification);

        return result;
    }

    @RequestMapping("/save")
    public Result save(@RequestBody SpecEntity specEntity){
        try {
            specService.save(specEntity);
            return new Result(true, "保存成功");
        }catch (Exception e){
            return new Result(false, "保存失败");
        }
    }

    @RequestMapping("/findSpecWithId")
    public SpecEntity findSpecWithId(Long id){
        return specService.findSpecWithId(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody SpecEntity specEntity){
        try {
            specService.update(specEntity);
            return new Result(true, "更新成功");
        }catch (Exception e){
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] idx){
        try {
            specService.delete(idx);
            return new Result(true, "删除成功");
        } catch (Exception e){
            return new Result(true, "删除失败");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        return specService.selectOptionList();
    }


}
