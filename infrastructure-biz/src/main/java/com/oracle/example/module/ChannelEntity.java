package com.oracle.example.module;


import lombok.Data;

import java.util.Date;

/**
 * 通道 实体类
 */

@Data
public class ChannelEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 通道名
     */
    private String name;

    /**
     * 排序,优先级,0-255
     */
    private Integer sort;

    /**
     * 缓存key,用于缓存
     */
    private String cacheName;

    /**
     * key,用于缓存/spring容器等
     */
    private String keyName;

    /**
     * 通道类型,对同一个ip的接口调用为同一类型
     */
    private Integer type;

    /**
     * 最大群发数
     */
    private Integer maxGroupNumber;

    /**
     * 支持的运营商. 0:未知;1:移动;2:联通;3.电信
     */
    private Integer supportOperator;

    /**
     * 最大连接数,针对socket
     */
    private Integer maxConnect;

    /**
     * 最大并发数,针对所有,类型相同,并发肯定一样
     */
    private Integer maxConcurrent;

    /**
     * 是否是cmpp
     */
    private Integer isCmpp;

    /**
     * 连接用的字符若干
     */
    private String aKey;
    private String bKey;
    private String cKey;
    private String dKey;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public ChannelEntity(String name) {
        this.name = name;
    }

    public ChannelEntity() {}
}