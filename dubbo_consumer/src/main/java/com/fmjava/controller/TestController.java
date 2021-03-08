package com.fmjava.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.service.TestDemo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference
    private TestDemo testDemo;

    @RequestMapping("/getname")
    public String getNme(){

        return testDemo.getName();
        // return "TestController --- getName";
    }
}
