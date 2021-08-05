package com.oracle.example.common;

import com.oracle.example.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {


    private int code;
    private String message;
    private T data;

    public Result (ResultCode resultCode,String message) {
        this.code = resultCode.code();
        this.message = message;
    }

    public Result(ResultCode resultCode, String message, T data) {
        this.code = resultCode.code();
        this.message = message;
        this.data = data;
    }
}
