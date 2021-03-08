package com.fmjava.core.pojo.entity;

import lombok.Data;

@Data
public class Result {
    private boolean success;
    private String message;

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
