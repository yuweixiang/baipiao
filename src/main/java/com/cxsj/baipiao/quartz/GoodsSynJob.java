package com.cxsj.baipiao.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GoodsSynJob {

    @Value("${jstAppKey}")
    private String jstAppKey;

    @Value("${jstAppSecret}")
    private String jstAppSecret;

    @Async
    @Scheduled(cron = "0 1 * * *")
    public void syncGoodsInfo(){

    }
}
