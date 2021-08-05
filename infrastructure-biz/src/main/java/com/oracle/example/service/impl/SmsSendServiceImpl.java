package com.oracle.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.oracle.example.controller.req.VariateSendDto;
import com.oracle.example.convert.SmsRecordEntityMapper;
import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.factory.ProcessorFactory;
import com.oracle.example.module.ChannelEntity;
import com.oracle.example.module.ResultDTO;
import com.oracle.example.processor.SendSmsProcessor;
import com.oracle.example.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsSendServiceImpl implements SmsSendService {


    @Autowired
    private ProcessorFactory processorFactory;

    @Override
    public ResultDTO variateSend(VariateSendDto req, ChannelEntity channelEntity) {
        SendSmsProcessor sendSmsProcessor = processorFactory.buildVariateSendSmsProcessor(channelEntity);
        return sendSmsProcessor.process(channelEntity,executeSmsSendRecord(req,channelEntity));
    }


    @Override
    public ResultDTO massMailingSend(VariateSendDto req, ChannelEntity channelEntity) {
        SendSmsProcessor sendSmsProcessor = processorFactory.buildMailingSendSmsProcessor(channelEntity);
        return sendSmsProcessor.process(channelEntity,executeSmsSendRecord(req,channelEntity));
    }

    private SmsSendRecordEntity executeSmsSendRecord(VariateSendDto req,ChannelEntity channelEntity){
        SmsSendRecordEntity smsSendRecordEntity = SmsRecordEntityMapper.instance.VariateSendReqToRecordEntity(req,channelEntity);
        smsSendRecordEntity.setRequestBody(JSON.toJSONString(req));
        return smsSendRecordEntity;
    }

}
