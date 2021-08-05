package com.oracle.example.module;


import lombok.Data;

/**
 * 修改短信发送记录所需信息
 * See
 */
@Data
public class UpdateRecordInfo<E> {

    //响应对象中的唯一记录号
    private String id;

    //响应对象中的响应码
    private String code;

    //响应对象中的异常消息
    private String error = "";

    //异常枚举类类型对象
    private Class<E> eClass;

    //异常枚举的成功枚举
    private E successEnum;

    public UpdateRecordInfo(String id, String code, Class<E> eClass, E successEnum) {
        this.id = id;
        this.code = code;
        this.eClass = eClass;
        this.successEnum = successEnum;
    }

    public UpdateRecordInfo(String id, String code, Class<E> eClass, E successEnum, String error) {
        this.id = id;
        this.code = code;
        this.error = error;
        this.eClass = eClass;
        this.successEnum = successEnum;
    }
}
