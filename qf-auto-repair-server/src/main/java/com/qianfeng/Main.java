package com.qianfeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableConfigurationProperties
@SpringBootApplication
@EnableCaching // 开启缓存注解
@EnableTransactionManagement // 开启注解方式的事务管理
@EnableScheduling // 开启任务调度
public class Main {  
    public static void main(String[] args) {  
        SpringApplication.run(Main.class,args);
    }  
}