package com.fmjava.core.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fmjava.core.pojo.entity.Result;
import com.fmjava.core.pojo.seller.Seller;
import com.fmjava.core.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class SellerControllser {

    @Reference
    private SellerService sellerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/add")
    public Result add(@RequestBody Seller seller){
        try {
            String password = seller.getPassword();
            String encode = passwordEncoder.encode(password);
            seller.setPassword(encode);

            sellerService.add(seller);
            return new Result(true, "注册成功! 等待运营商审核");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "注册失败!");
        }
    }

}
