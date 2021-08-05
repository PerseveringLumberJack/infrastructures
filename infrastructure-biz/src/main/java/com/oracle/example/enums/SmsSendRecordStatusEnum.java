package com.oracle.example.enums;


import java.io.Serializable;

/**
 *
 * 短信发送任务状态枚举
 */
public enum SmsSendRecordStatusEnum implements Serializable {

	DEFAULT(0, "默认"),
	SYNC_SUCCESS(1, "同步成功"),
	ASYNC_SUCCESS(2, "异步成功"),
	SYNC_FAILED(-1,"同步失败"),
	ASYNC_FAILED(-2, "异步失败"),
	;


	private Integer code;
	private String message;

	SmsSendRecordStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return code;
	}

	public String message() {
		return message;
	}
}
