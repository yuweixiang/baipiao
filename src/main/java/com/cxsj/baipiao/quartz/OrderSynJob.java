package com.cxsj.baipiao.quartz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.dal.dao.OrderAddressMapper;
import com.cxsj.baipiao.dal.dao.OrderGoodsMapper;
import com.cxsj.baipiao.dal.dao.OrderMapper;
import com.cxsj.baipiao.dal.dao.UserMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.domain.User;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.integration.JstWrapper;
import com.cxsj.baipiao.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OrderSynJob {

    @Resource
    private JstWrapper jstWrapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    private static final int PAGE_SIZE = 20;

    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncOrdersInfo(){

        int totalNum = orderMapper.countByStatus(OrderStatusEnum.PAID.getCode());
        log.info("[订单数据同步]开始处理订单，一共：{}条",totalNum);
        for (int i=0;i< (totalNum / PAGE_SIZE)+1 ;i++){
            try {
                List<Order> orders = orderMapper.queryByStatus(OrderStatusEnum.PAID.getCode(),
                        i * PAGE_SIZE, PAGE_SIZE);
                if (CollectionUtils.isEmpty(orders)) {
                    log.info("无订单，无需处理，直接跳过");
                    return;
                }
                syncOrder(orders);
                updateOuterIds(orders);
            }catch (Exception e){
                log.info("同步失败！e",e);
            }

        }
    }

    private void updateOuterIds(List<Order> orders) {

        orders.forEach(order -> {
            if (StringUtils.isNotBlank(order.getOuterId())){
                orderMapper.updateOuterId(order.getId(),order.getOuterId());
            }
        });

    }

    private void syncOrder(List<Order> orders){
        JSONArray array = new JSONArray();
        for(Order order : orders) {
            User user = userMapper.queryById(order.getUserId());
            Goods goods = orderGoodsMapper.queryByOrder(order.getId());
            Address address = orderAddressMapper.queryByOrder(order.getId());
            JSONObject object = new JSONObject();
            object.put("shop_id", 15940074);
            object.put("so_id", order.getId().toString());
            object.put("order_date", DateUtil.getNewFormatDateString(order.getGmtCreate()));
            object.put("shop_status", "WAIT_SELLER_SEND_GOODS");
            object.put("shop_buyer_id", user.getNick());
            object.put("receiver_state", address.getProvince());
            object.put("receiver_city", address.getCity());
            object.put("receiver_district", address.getArea());
            object.put("receiver_address", address.getDetail());
            object.put("receiver_name", address.getReceiveName());
            object.put("receiver_phone", address.getTelephone());
            object.put("pay_amount", order.getPrice());
            object.put("freight", 0);
            object.put("items", buildItems(goods));
            object.put("pay","");
            array.add(object);
        }
        String result = jstWrapper.requestJst("https://openapi.jushuitan.com/open/jushuitan/orders/upload",array.toJSONString());
        log.info("同步订单结果:{}",result);
        JSONArray jsonArray = JSONObject.parseObject(result).getJSONObject("data").getJSONArray("datas");

        fillOuterId(orders,jsonArray);
    }

    private void fillOuterId(List<Order> orders, JSONArray jsonArray) {
        if (jsonArray == null){
            return;
        }
        Map<String,String> map = new HashMap<>();
        for (int i=0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            map.put(object.getString("so_id"),object.getString("o_id"));
        }

        orders.forEach(order -> {
            order.setOuterId(map.get(order.getId().toString()));
        });
    }


    private JSONArray buildItems(Goods goods) {
        JSONObject object = new JSONObject();
        object.put("sku_id",goods.getTitle());
        object.put("shop_sku_id",goods.getTitle());
        object.put("amount",goods.getPrice());
        object.put("base_price",goods.getPrice());
        object.put("name",goods.getTitle());
        object.put("qty",goods.getNum());
        object.put("outer_oi_id",goods.getId().toString());
        JSONArray array = new JSONArray(1);
        array.add(object);
        return array;
    }
}
