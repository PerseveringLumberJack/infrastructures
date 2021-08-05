package com.oracle.example.factory;


import com.oracle.example.config.universal.ConfigStore;
import com.oracle.example.loadbalance.LoadBalance;
import com.oracle.example.loadbalance.ServerNode;
import com.oracle.example.module.ChannelEntity;
import com.oracle.example.processor.SendSmsProcessor;
import com.oracle.example.send.ChuangLanMassMailingSendSmsProcessor;
import com.oracle.example.send.ChuangLanVariateSendSmsProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 自定义的各类处理器工厂
 * 这里先做简单的初始化
 * 后期可对该工厂进一步封装
 */
@Component
public class ProcessorFactory {

    @Autowired
    private ConfigStore configStore;

    @Autowired
    private LoadBalance loadBalance;

    private static List<ServerNode> variateServersList = new CopyOnWriteArrayList<>();

    private static List<ServerNode> mailingServersList = new CopyOnWriteArrayList<>();

    private static Map<String, SendSmsProcessor> sendVariateSmsProcessorMap = new ConcurrentHashMap<>();

    private static Map<String, SendSmsProcessor> sendMailingSmsProcessorMap = new ConcurrentHashMap<>();

    @Autowired
    private  ChuangLanMassMailingSendSmsProcessor massMailingSendSmsProcessor;

    @Autowired
    private  ChuangLanVariateSendSmsProcessor variateSendSmsProcessor;

    @PostConstruct
    public void init() {
        sendVariateSmsProcessorMap.put(configStore.VARIATE(),variateSendSmsProcessor);
        sendMailingSmsProcessorMap.put(configStore.MAILING(),massMailingSendSmsProcessor);
        if(configStore.WEIGHT()>0)
            variateServersList.add(new ServerNode(configStore.VARIATE(), configStore.WEIGHT(), 0));
            mailingServersList.add(new ServerNode(configStore.VARIATE(), configStore.WEIGHT(), 0));

    }

    public  SendSmsProcessor buildVariateSendSmsProcessor(ChannelEntity channel) {
        return sendVariateSmsProcessorMap.get(queryNodeName(variateServersList,channel));
    }

    public  SendSmsProcessor buildMailingSendSmsProcessor(ChannelEntity channel) {
        return sendMailingSmsProcessorMap.get(queryNodeName(mailingServersList,channel));
    }

    private String queryNodeName(List list,ChannelEntity channel){
        return configStore.WEIGHT()>0?loadBalance.select(list).getNodeName():channel.getName();
    }
}
