package com.cxsj.baipiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages={
        "com.cxsj.baipiao.dal.dao",
        "com.cxsj.baipiao.integration",
        "com.cxsj.baipiao.quartz",
        "com.cxsj.baipiao.service"})
@SpringBootApplication
public class TestServiceConfiguration {
    public  static  void main(String[] args){
        SpringApplication.run(TestServiceConfiguration.class, args);
    }
}