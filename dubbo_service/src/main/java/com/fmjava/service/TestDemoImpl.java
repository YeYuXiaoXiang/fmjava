package com.fmjava.service;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public class TestDemoImpl implements TestDemo {
    @Override
    public String getName() {
        return "fmjava _ dubbo";
    }
}
