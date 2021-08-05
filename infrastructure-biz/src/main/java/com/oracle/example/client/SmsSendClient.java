package com.oracle.example.client;

import com.oracle.example.controller.req.VariateSendReq;
import com.oracle.example.module.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cg-eureka-sms-client")
public interface SmsSendClient {

    @PostMapping("/send/variate")
    ResultDTO variateSend(@RequestBody VariateSendReq req);

    @PostMapping("/send/massmailing")
    ResultDTO massMailingSend(@RequestBody VariateSendReq req);
}
