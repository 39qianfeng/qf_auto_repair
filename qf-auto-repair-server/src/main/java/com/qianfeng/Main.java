package com.qianfeng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@MapperScan("com.qianfeng.mapper") //mapper接口所在的的位置！
@EnableConfigurationProperties
@SpringBootApplication
public class Main {  
    public static void main(String[] args) {  
        SpringApplication.run(Main.class,args);
    }  
}