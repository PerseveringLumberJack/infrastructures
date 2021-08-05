package com.oracle.example.controller;

import com.oracle.example.config.universal.ConfigStore;
import com.oracle.example.controller.req.VariateSendDto;
import com.oracle.example.controller.req.VariateSendReq;
import com.oracle.example.convert.SmsRecordEntityMapper;
import com.oracle.example.module.ResultDTO;
import com.oracle.example.service.SmsSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/send")
@PropertySource("classpath:chuanglan-application.properties")
public class SmsSendController {


    @Autowired
    private SmsSendService smsSendService;

    @Autowired
    private ConfigStore configStore;

    @ResponseBody
    @PostMapping("/variate")
    public ResultDTO variateSend(@RequestBody VariateSendReq req){
        VariateSendDto sendDto = SmsRecordEntityMapper.instance.convertVariateSendReq(req);
        return smsSendService.variateSend(sendDto,configStore.variateChannelEntity(req.getChannel()));
    }

    @ResponseBody
    @PostMapping("/massmailing")
    public ResultDTO massMailingSend(@RequestBody VariateSendReq req){
        VariateSendDto sendDto = SmsRecordEntityMapper.instance.convertVariateSendReq(req);
        return smsSendService.massMailingSend(sendDto,configStore.mailingChannelEntity(req.getChannel()));
    }
}
