package com.oracle.example.service.impl;

import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.repository.SmsSendRecordRepository;
import com.oracle.example.service.BatchService;
import com.oracle.example.service.SmsSendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Component
public class BatchServiceImpl {

    @Autowired
    private SmsSendRecordService smsSendRecordService;

    public void batchUpdateSmsSendRecord(List<SmsSendRecordEntity> recordEntityList) {

       smsSendRecordService.saveBatch(recordEntityList);
        //批量操作
    }
}
