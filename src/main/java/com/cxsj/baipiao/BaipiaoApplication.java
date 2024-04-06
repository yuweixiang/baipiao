package com.cxsj.baipiao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BaipiaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaipiaoApplication.class, args);
    }

}
