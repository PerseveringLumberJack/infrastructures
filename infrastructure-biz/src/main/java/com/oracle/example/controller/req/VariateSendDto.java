package com.oracle.example.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@ApiModel(value = "短信发送请求封装参数")
@Data
public class VariateSendDto implements Serializable {

    private static final long serialVersionUID = 3L;

    @ApiModelProperty("API账号（必填）")
    private String account;

    @ApiModelProperty("API账号对应密钥（必填）")
    private String password;

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

}
