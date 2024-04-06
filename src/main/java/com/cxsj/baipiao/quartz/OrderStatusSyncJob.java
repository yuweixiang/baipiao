package com.cxsj.baipiao.quartz;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.dal.dao.OrderMapper;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.integration.JstWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderStatusSyncJob {


    @Resource
    private JstWrapper jstWrapper;

    @Resource
    private OrderMapper orderMapper;

    private static final int PAGE_SIZE = 20;

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncOrdersStatus(){

        int totalNum = orderMapper.countWaitSyncOrder();
        log.info("[订单状态同步]开始处理订单，一共：{}条",totalNum);
        for (int i=0;i< (totalNum / PAGE_SIZE)+1 ;i++){
            try {
                List<Order> orders = orderMapper.queryWaitSynOrder(
                        i * PAGE_SIZE, PAGE_SIZE);
                if (CollectionUtils.isEmpty(orders)) {
                    log.info("无订单，无需处理，直接跳过");
                    return;
                }
                syncOrderStatus(orders);

            }catch (Exception e){
                log.info("同步失败！e",e);
            }

        }
    }

    private void syncOrderStatus(List<Order> orders) {
        Map<Long,Order> map = orders.stream().collect(Collectors.toMap(order -> order.getId(), Function.identity()));

        List<Long> list = orders.stream().filter(e->StringUtils.isNotBlank(e.getOuterId()))
                .map(e->Long.parseLong(e.getOuterId())).collect(Collectors.toList());
        JSONObject object = new JSONObject();
        object.put("o_ids", list);
        String result = jstWrapper.requestJst("https://openapi.jushuitan.com/open/orders/single/query",object.toJSONString());
        log.info("[订单状态同步]-jst结果:{}",result);
        JSONArray array = JSONObject.parseObject(result).getJSONObject("data").getJSONArray("orders");

        fillLogisticsStatus(array,map);
    }

    private void fillLogisticsStatus(JSONArray array,Map<Long,Order> map) {
        for (int i=0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            String status = object.getString("shop_status");
            Long orderId = object.getLong("so_id");
            if (StringUtils.equals(status,"WAIT_SELLER_SEND_GOODS")){
                return;
            }
            if (StringUtils.equals(status,"WAIT_BUYER_CONFIRM_GOODS") &&
                    StringUtils.equals(map.get(orderId).getStatus(),"PAID")){
                String ldNod = object.getString("l_id");
                String logisticsCompany = object.getString("logistics_company");
                orderMapper.updateLogisticsInfo(orderId,logisticsCompany,ldNod);
            }
            if (StringUtils.equals(status,"TRADE_FINISHED") ){
                orderMapper.updateStatus(orderId,"COMPLETE");
            }
            if (StringUtils.equals(status,"TRADE_CLOSED") ||
                    StringUtils.equals(status,"TRADE_CLOSED_BY_TAOBAO")){
                orderMapper.updateStatus(orderId,"CANCELED");
            }
        }
    }
}
