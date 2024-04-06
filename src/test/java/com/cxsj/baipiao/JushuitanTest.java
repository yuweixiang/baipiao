package com.cxsj.baipiao;

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
import com.cxsj.baipiao.integration.JstWrapper;
import com.cxsj.baipiao.quartz.OrderStatusSyncJob;
import com.cxsj.baipiao.quartz.OrderSynJob;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;

public class JushuitanTest extends BaseServiceTest{

    @Resource
    private JstWrapper jstWrapper;

    @Resource
    private OrderSynJob goodsSynJob;
    @Resource
    private OrderStatusSyncJob orderStatusSyncJob;

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    @Test
    public void testGetData(){

        JSONObject object = new JSONObject();
        object.put("sku_ids","B37随机颜色1支装");
        String result = jstWrapper.requestJst("https://openapi.jushuitan.com/open/sku/query",object.toJSONString());
        System.out.println(result);
    }
    @Test
    public void testSynOrders(){
        goodsSynJob.syncOrdersInfo();
    }
    @Test
    public void testSynOrderStatus(){
        orderStatusSyncJob.syncOrdersStatus();
    }

    @Test
    public void testSynOrder(){

        Order order = orderMapper.queryByOrderId(2024033021L);
        User user = userMapper.queryById(order.getUserId());
        Goods goods = orderGoodsMapper.queryByOrder(order.getId());
        Address address = orderAddressMapper.queryByOrder(2024033021L);
        JSONArray array = new JSONArray(1);
        JSONObject object = new JSONObject();
        object.put("shop_id",15940074);
        object.put("so_id",order.getId().toString());
        object.put("order_date","2024-04-01 14:11:22");
        object.put("shop_status","WAIT_SELLER_SEND_GOODS");
        object.put("shop_buyer_id",user.getNick());
        object.put("receiver_state",address.getProvince());
        object.put("receiver_city",address.getCity());
        object.put("receiver_district",address.getArea());
        object.put("receiver_address",address.getDetail());
        object.put("receiver_name",address.getReceiveName());
        object.put("receiver_phone",address.getTelephone());
        object.put("pay_amount",order.getPrice());
        object.put("freight",0);
        object.put("items",buildItems(goods));
        array.add(object);
        String result = jstWrapper.requestJst("https://openapi.jushuitan.com/open/jushuitan/orders/upload",array.toJSONString());
        System.out.println(result);
    }

    @Test
    public void testOrderStatusChange(){
        String o_id = "1528134";
        JSONObject object = new JSONObject();
        object.put("o_ids", Lists.newArrayList(o_id));
        String result = jstWrapper.requestJst("https://openapi.jushuitan.com/open/orders/single/query",object.toJSONString());
        System.out.println(result);
    }

    @Test
    public void testQueryOrder(){
        JSONObject object = new JSONObject();
        object.put("modified_begin", "2024-04-06 00:00:00");
        object.put("modified_end", "2024-04-06 23:00:00");
        object.put("page_index", 1);
        object.put("page_size", 20);
        String result = jstWrapper.requestJst("https://openapi.jushuitan.com/open/orders/single/query",object.toJSONString());
        System.out.println(result);
    }

    private JSONArray buildItems(Goods goods) {
        JSONObject object = new JSONObject();
        object.put("sku_id","B37随机颜色1支装");
        object.put("shop_sku_id","B37随机颜色1支装");
        object.put("amount",1);
        object.put("base_price",0.55);
        object.put("name","B37随机颜色1支装");
        object.put("qty",goods.getNum());
        object.put("outer_oi_id",goods.getId().toString());
        JSONArray array = new JSONArray(1);
        array.add(object);
        return array;
    }
}
