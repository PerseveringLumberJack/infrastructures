package com.oracle.example.convert;

import com.oracle.example.controller.req.VariateSendDto;
import com.oracle.example.controller.req.VariateSendReq;
import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.module.ChannelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SmsRecordEntityMapper {


    SmsRecordEntityMapper instance = Mappers.getMapper(SmsRecordEntityMapper.class);


    /**
     * 后续一一指定参数的映射关系
     * @param req
     * @return
     */
    @Mappings({
            @Mapping(target = "channelName",source = "channelEntity.name"),
            @Mapping(target = "message", source = "req.msg"),
            @Mapping(target = "phones",source = "req.mobile"),
            @Mapping(target = "phoneCount",expression = "java(req.getMobile().split(\",\").length)"),
            @Mapping(target = "createTime",expression = "java(new java.util.Date())"),
            @Mapping(target = "updateTime",expression = "java(new java.util.Date())")

    })
    SmsSendRecordEntity VariateSendReqToRecordEntity(VariateSendDto req, ChannelEntity channelEntity);

    VariateSendDto convertVariateSendReq(VariateSendReq req);
}
