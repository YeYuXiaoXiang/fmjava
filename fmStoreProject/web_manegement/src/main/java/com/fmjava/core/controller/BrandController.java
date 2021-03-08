package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.PageResult;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.good.Brand;
import com.fmjava.core.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    @RequestMapping("/findAllBrands")
    public List<Brand> getBrands(){
        return brandService.findAllBrands();
    }

    /*定义分页的数据接口*/
    @RequestMapping("/findPage")
    public PageResult findPage(Integer page, Integer pageSize,@RequestBody Brand brand){
        PageResult pageResult = brandService.findPage(page, pageSize,brand);
        return pageResult;
    }

    /*添加品牌*/
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand){
        try{
            brandService.addBrand(brand);
            return new Result(true, "添加成功");
        }catch (Exception e){
            return new Result(false, "添加失败");
        }
    }

    /*修改数据回显，根据id*/
    @RequestMapping("/findById")
    public Brand findById(Long id){
        Brand brand = brandService.findById(id);
        System.out.println(brand);
        return brand;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand){
        try {
            brandService.update(brand);
            return new Result(true, "添加成功");
        } catch (Exception e){
            return new Result(true, "添加失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] idx){
        try {
            brandService.delete(idx);
            return new Result(true, "删除成功");
        } catch (Exception e){
            return new Result(true, "删除失败");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }

}
