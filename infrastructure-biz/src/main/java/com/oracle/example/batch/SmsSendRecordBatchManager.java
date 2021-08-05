package com.oracle.example.batch;

import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.service.BatchService;
import com.oracle.example.service.impl.BatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 短信发送记录 批处理管理器
 */
@Component
@Slf4j
public class SmsSendRecordBatchManager {
	/**
	 * 要修改的短信发送记录集合
	 */
	private List<SmsSendRecordEntity> updates = new LinkedList<>();


	/**
	 * 定时线程池
	 */
	private ExecutorService scheduleSaveExecutor;

	@Autowired
	private BatchServiceImpl batchService;


	/**
	 * 要修改的记录 增加
	 */
	public void addRecordEntity(SmsSendRecordEntity record) {

		updates.add(record);
		//if (updates.size() > 100) {
			batchSave();
		//}

	}

	/**
	 * 定时线程保存
	 */
	public void scheduleSaveStart() {
		scheduleSaveExecutor = Executors.newFixedThreadPool(10);
		scheduleSaveExecutor.submit(() -> {
			try {
				if(updates.size() > 0)
					batchSave();
			} catch (Exception e) {
				log.error("[短信发送记录 批处理管理器]定时批量保存异常.e:{}", e.getMessage(), e);
			}
		});
	}

	/**
	 * 保存
	 */
	public void batchSave() {
		synchronized (this) {
			try {
				log.info("[短信发送记录 批处理管理器]批量保存开始.");
					batchService.batchUpdateSmsSendRecord(updates);
				log.info("[短信发送记录 批处理管理器]批量保存完成.");
			} catch (Exception e) {
				log.error("[短信发送记录 批处理管理器]批量保存异常.e:{}", e.getMessage(), e);
			}finally {
				updates.clear();
			}
		}
	}

}
