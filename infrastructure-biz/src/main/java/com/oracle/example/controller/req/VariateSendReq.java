package com.oracle.example.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@ApiModel(value = "短信发送请求参数")
@Data
public class VariateSendReq implements Serializable {

    private static final long serialVersionUID = 3L;

    @ApiModelProperty("短信内容（必填）")
    private String msg;

    @ApiModelProperty("手机号（必填）")
    private String mobile;

    @ApiModelProperty("自定义批次号（选填）")
    private String uid;

    @ApiModelProperty("发件人（选填）")
    private String senderid;

    @ApiModelProperty("退订开关（选填）")
    private Integer tdflag;

    @ApiModelProperty("短信通道")
    private String channel;

}
