package com.oracle.example.service.impl;

import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.repository.SmsSendRecordRepository;
import com.oracle.example.service.SmsSendRecordService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("smsSendRecordService")
public class SmsSendRecordServiceImpl extends ServiceImpl<SmsSendRecordRepository, SmsSendRecordEntity> implements SmsSendRecordService {


}
