package com.oracle.example.processor;

import com.oracle.example.batch.SmsSendRecordBatchManager;
import com.oracle.example.entity.SmsSendRecordEntity;
import com.oracle.example.enums.ErrorEnum;
import com.oracle.example.enums.SmsSendRecordStatusEnum;
import com.oracle.example.module.ChannelEntity;
import com.oracle.example.module.ResultDTO;
import com.oracle.example.module.UpdateRecordInfo;
import com.oracle.example.service.SmsSendRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Slf4j
@Component
public abstract class AbstractSendSmsProcessor<T, P, E> implements SendSmsProcessor {

    protected SmsSendRecordBatchManager smsSendRecordBatchManager;

    @Autowired
    public void init(SmsSendRecordBatchManager smsSendRecordBatchManager) {

        this.smsSendRecordBatchManager = smsSendRecordBatchManager;
    }

    /**
     * 发送并获得响应
     * @param object
     * @return
     */
    protected abstract String send(T object);

    /**
     * 获取响应状态
     * @param r
     * @return
     */
    protected abstract UpdateRecordInfo<E> queryUpdateRecordInfo(P r);


    protected abstract P toResponseObject(String result);

    /**
     * 转为请求对象
     */
    protected abstract T toRequestObject(ChannelEntity channel, SmsSendRecordEntity record);

    @Transactional
    @Override
    public ResultDTO<P> process(ChannelEntity channel, SmsSendRecordEntity record) {

        String result = send(toRequestObject(channel, record));

        P response = toResponseObject(result);

        updateRecord(response, record);

        return ResultDTO.success(response);

    }

    /**
     * 将同步响应更新到发送记录
     *
     * @param response
     * @param record
     * @return
     */
    protected void updateRecord(P response, SmsSendRecordEntity record) {
        try {
            if (Objects.nonNull(response)) {
                UpdateRecordInfo<E> updateRecordInfo = queryUpdateRecordInfo(response);

                if (updateRecordInfo.getError() != null)
                    record.setErrorInfo(updateRecordInfo.getError());

                record.setErrorInfo(ErrorEnum.SUCCESS.message());
                record.setStatus(SmsSendRecordStatusEnum.SYNC_SUCCESS.code());
                smsSendRecordBatchManager.addRecordEntity(record);
            }
        } catch (Exception e) {
            log.error("[短信发送过程]响应对象保存到记录失败");
        }
    }
}
