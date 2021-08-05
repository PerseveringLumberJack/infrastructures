package com.oracle.example.common;

import com.oracle.example.enums.ResultCode;

public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    private static final String DEFAULT_FAIL_MESSAGE = "SUCCESS";

    public static Result genSuccessResult() {
        return new Result(ResultCode.SUCCESS,DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> Result<T> genSuccessResult(T data) {
        return new Result(ResultCode.SUCCESS,DEFAULT_SUCCESS_MESSAGE,data);
    }

    public static Result genFailResult(String message) {
        return new Result(ResultCode.FAIL,DEFAULT_FAIL_MESSAGE);
    }
}
