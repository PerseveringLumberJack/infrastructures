package com.oracle.example.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.net.URLDecoder;
import java.util.Date;

/**
 *
 * 创蓝
 */
public interface ChuangLanDto {

	//普通发送接口响应
	@Data
	class Response {
		private String time;//响应时间
		private String msgid;//消息id
		private String code;//状态码
		private String errorMsg;//状态码说明
	}


	//变量发送请求
	@Data
	class VariateRequest {
		private String url;
		private String requestBody;


		public VariateRequest(String url, String requestBody) {
			this.url = url;
			this.requestBody = requestBody;
		}


	}

	//变量发送响应
	@Data
	class VariateResponse {
		private String msgid;//消息id
		private String error;//状态码说明
		private String code;//状态码
	}

	//变量发送响应
	@Data
	class MassMailingResponse {
		private String msgid;//消息id
		private String message;//状态码说明
		private String code;//状态码
		private MassMailing data;

		@Data
		static class MassMailing{
			private String messageId;
			private String errorPhone;
		}
	}

}
