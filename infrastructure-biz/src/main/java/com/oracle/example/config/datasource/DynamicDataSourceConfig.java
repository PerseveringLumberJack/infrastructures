/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.oracle.example.config.datasource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 */
//@Configuration
public class DynamicDataSourceConfig implements TransactionManagementConfigurer {

    @Resource(name="prodTransactionManager")
    private PlatformTransactionManager platformTransactionManager;

    /**
     * 创建 DataSource Bean
     * */
    
    @Bean
    @ConfigurationProperties("spring.datasource.druid.first")
    public DataSource oneDataSource(){
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }

    @Bean
    @ConfigurationProperties("spring.datasource.druid.second")
    public DataSource twoDataSource(){
        DataSource dataSource = DruidDataSourceBuilder.create().build();
        return dataSource;
    }
    
    
    /**
     * 如果还有数据源,在这继续添加 DataSource Bean
     * */
    
    @Bean
    @Primary
    public DynamicDataSource dataSource(DataSource oneDataSource, DataSource twoDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>(3);
        targetDataSources.put(DataSourceNames.FIRST, oneDataSource);
        targetDataSources.put(DataSourceNames.SECOND, twoDataSource);
        // 还有数据源,在targetDataSources中继续添加
        System.out.println("DataSources:" + targetDataSources);
        return new DynamicDataSource(oneDataSource, targetDataSources);
    }



    @Bean(name = "prodTransactionManager")
    public PlatformTransactionManager prodTransactionManager(@Qualifier("oneDataSource") DataSource oneDataSource) {
        return new DataSourceTransactionManager(oneDataSource);
    }

    @Bean(name = "devTransactionManager")
    public PlatformTransactionManager devTransactionManager(@Qualifier("twoDataSource") DataSource twoDataSource) {
        return new DataSourceTransactionManager(twoDataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return platformTransactionManager;
    }


}