package com.oracle.example.enums;


public enum ResultCode {

    SUCCESS(200,"成功"),

    FAIL(400,"失败"),

    UNAUTHORIZED(401,"未认证（签名错误）"),

    NOT_FOUND(404,"接口不存在"),

    INTERNAL_SERVER_ERROR(500,"服务器错误");

    private final int code;

    private String message;

    ResultCode(int code,String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }
}
