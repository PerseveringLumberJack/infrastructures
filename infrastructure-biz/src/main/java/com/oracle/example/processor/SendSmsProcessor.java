package com.oracle.example.processor;


import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.module.ChannelEntity;
import com.oracle.example.module.ResultDTO;

/**
 *
 * 发送短信处理器
 */
public interface SendSmsProcessor<P> {


	/**
	 * 处理方法,包装下
	 */
	ResultDTO<P> process(ChannelEntity channel, SmsSendRecordEntity record);


}
