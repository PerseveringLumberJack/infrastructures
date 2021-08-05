package com.oracle.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.oracle.example.config.universal.IdAnnotation;
import com.oracle.example.enums.SmsSendRecordStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * 短信发送记录
 */

@Data
@TableName("send_record_entity")
public class SmsSendRecordEntity {
    /**
     * id
     */
    @Id
    @IdAnnotation
    private Long id;


    /**
     * 平台发送记录id,当接口调用发送时
     */
    @TableField("platform_send_sms_record_id")
    private Long platformSendSmsRecordId;

    /**
     * 平台id,当接口调用时
     */
    @TableField("platform_id")
    private Long platformId;

    /**
     * 任务id,,当用户操作发送时
     */
    @TableField("send_task_id")
    private Long sendTaskId;

    /**
     * 通道id
     */
    @TableField("channel_id")
    private Long channelId;

    /**
     * 通道名
     */
    @TableField("channel_name")
    private String channelName;

    /**
     * 发送手机号
     */
    @TableField("phones")
    private String phones;

    /**
     * 手机号总数
     */
    @TableField("phone_count")
    private Integer phoneCount;


    /**
     * 发送消息
     */
    @TableField("message")
    private String message;

    /**
     * 调用者请求url
     */
    @TableField("url")
    private String url;

    /**
     * 调用者请求对象json字符
     */
    @TableField("request_body")
    private String requestBody;

    /**
     * 其他id,一般为接口返回的该次调用唯一标识
     */
    @TableField("other_id")
    private String otherId;

    /**
     * 同步回调时间
     */
    @TableField("sync_time")
    private Date syncTime;

    /**
     * 同步返回对象json字符
     */
    @TableField("sys_result_body")
    private String syncResultBody;

    /**
     * 异步回调时间
     */
    @TableField("async_time")
    private Date asyncTime;

    /**
     * 异步返回对象json字符
     */
    @TableField("asys_result_body")
    private String asyncResultBody;

    /**
     * 异常信息,如果有的话
     */
    @TableField("error_info")
    private String errorInfo;

    /**
     * 状态. 0:默认;1:同步成功;2:异步成功;-1:同步失败;-2:异步失败
     */
    @TableField("status")
    private Integer status = SmsSendRecordStatusEnum.DEFAULT.code();

    /**
     * 创建时间,也是发送时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;


    public SmsSendRecordEntity(Long sendTaskId, Long channelId, String channelName, String phones, Integer phoneCount, String message) {
        this.sendTaskId = sendTaskId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.phones = phones;
        this.phoneCount = phoneCount;
        this.message = message;
    }

    public SmsSendRecordEntity(Long platformSendSmsRecordId, Long platformId, Long channelId, String channelName, String phones, Integer phoneCount, String message) {
        this.platformSendSmsRecordId = platformSendSmsRecordId;
        this.platformId = platformId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.phones = phones;
        this.phoneCount = phoneCount;
        this.message = message;
    }

    /**
     * 是否是定时任务发送记录
     */
    public boolean isTaskRecord() {
        return sendTaskId != null;
    }

    public SmsSendRecordEntity() {
    }
}