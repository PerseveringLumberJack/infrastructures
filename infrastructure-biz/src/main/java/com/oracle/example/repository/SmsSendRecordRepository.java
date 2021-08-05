package com.oracle.example.repository;

import com.oracle.example.entity.SmsSendRecordEntity;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface SmsSendRecordRepository extends BaseMapper<SmsSendRecordEntity> {


}
