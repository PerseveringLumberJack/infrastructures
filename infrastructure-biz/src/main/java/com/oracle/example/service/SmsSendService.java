package com.oracle.example.service;

import com.oracle.example.controller.req.VariateSendDto;
import com.oracle.example.module.ChannelEntity;
import com.oracle.example.module.ResultDTO;

public interface SmsSendService {

    ResultDTO variateSend(VariateSendDto req, ChannelEntity channelEntity);

    ResultDTO massMailingSend(VariateSendDto req, ChannelEntity channelEntity);
}
