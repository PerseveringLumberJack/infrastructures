package com.oracle.example.module;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;


@ApiModel(value = "基础实体类")
@Data
public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("账号（必填）")
    private String account;

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
