package com.oracle.example.send;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oracle.example.config.universal.ConfigStore;
import com.oracle.example.controller.req.VariateSendDto;
import com.oracle.example.dto.ChuangLanDto;
import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.enums.ChuangLanErrorEnum;
import com.oracle.example.module.ChannelEntity;
import com.oracle.example.module.UpdateRecordInfo;
import com.oracle.example.processor.AbstractSendSmsProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChuangLanMassMailingSendSmsProcessor extends AbstractSendSmsProcessor<ChuangLanDto.VariateRequest, ChuangLanDto.MassMailingResponse, ChuangLanErrorEnum> {

    @Autowired
    private ConfigStore configStore;

    @Override
    protected String send(ChuangLanDto.VariateRequest object) {

        try {
            return HttpUtil.post(object.getUrl(), object.getRequestBody());
        } catch (Exception e) {
            log.error("[短信发送过程]短信发送http失败.e:{}",e.getMessage(),e);
        }
        return null;
    }

    @Override
    protected UpdateRecordInfo queryUpdateRecordInfo(ChuangLanDto.MassMailingResponse r) {

        return new UpdateRecordInfo<>(r.getMessage(),r.getCode(),
                ChuangLanErrorEnum.class,ChuangLanErrorEnum.SUCCESS);
    }

    @Override
    protected ChuangLanDto.MassMailingResponse toResponseObject(String result) {
        if(StringUtils.isNotEmpty(result)){
            JSONObject jsonObject = JSON.parseObject(result);
            return jsonObject.toJavaObject(ChuangLanDto.MassMailingResponse.class);
        }
        return null;
    }


    @Override
    protected ChuangLanDto.VariateRequest toRequestObject(ChannelEntity channel, SmsSendRecordEntity record) {
        if(StringUtils.isNotEmpty(record.getRequestBody())){
            JSONObject jsonObject = JSON.parseObject(record.getRequestBody());
            VariateSendDto sendDto = jsonObject.toJavaObject(VariateSendDto.class);
            String msg = sendDto.getMsg();
            sendDto.setAccount(configStore.getAccount());
            sendDto.setPassword(configStore.getPassword());
            sendDto.setMsg(String.format(configStore.getFormworkmsg(),msg));
            record.setRequestBody(JSON.toJSONString(sendDto));
        }
        return new ChuangLanDto.VariateRequest(configStore.CHUANGLANURLPRE()+configStore.SEND(),record.getRequestBody());
    }

}
