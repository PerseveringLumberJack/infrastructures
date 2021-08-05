package com.oracle.example.service;


import com.oracle.example.entity.SmsSendRecordEntity;
import java.util.List;

/**
 *
 * 批量操作相关
 */
public interface BatchService {



	/**
	 * 批量修改保存
	 */
	 void batchUpdateSmsSendRecord(List<SmsSendRecordEntity> recordEntityList);

}
