package com.oracle.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@Configuration
@Slf4j
@SpringBootApplication(scanBasePackages = "com.oracle.example.*")
public class InfrastructureBizApplication {


    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext context = SpringApplication.run(InfrastructureBizApplication.class, args);

        Environment environment = context.getBean(Environment.class);

        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        log.info(" 访问地址: HTTP://{}:{}\t",hostAddress,environment.getProperty("server.port"));



    }

}
