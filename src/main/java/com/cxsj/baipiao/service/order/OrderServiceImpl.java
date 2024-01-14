package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.dal.dao.*;
import com.cxsj.baipiao.dal.dataObject.OrderDO;
import com.cxsj.baipiao.domain.*;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.exception.BizException;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cxsj.baipiao.enums.ResultCodeEnum.LACK_OF_POINT;

@Service
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private GoodsMapper goodsMapper;
    @Resource
    private SkuMapper skuMapper;

    @Resource
    private SequenseMapper sequenseMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public Long createOrder(Order order) {

        reducePoint(order);

        Long orderId = generateOrderId();
        order.setStatus(OrderStatusEnum.CREATED.getCode());
        order.setId(orderId);
        orderMapper.insert(order);
        orderAddressMapper.insert(orderId,order.getOrderAddress());
        orderGoodsMapper.insert(orderId,order.getOrderGoods());

        return order.getId();
    }

    public Order orderRender(Long userId, Goods goods){

        Address address = addressMapper.queryDefaultAddress(userId);

        Goods renderGoods = goodsMapper.queryById(goods.getId());
        Sku sku = skuMapper.queryById(goods.getSkuList().get(0).getId());
        renderGoods.setSkuList(Lists.newArrayList(sku));
        Order order = new Order();
        order.setOrderGoods(renderGoods);
        order.setOrderAddress(address);
        order.setPrice(sku.getPrice());
        return order;
    }

    private Long generateOrderId() {
        Long sequenceId = sequenseMapper.getId();

        Date d = new Date();
        SimpleDateFormat sbf = new SimpleDateFormat("yyyyMMdd");
        String date = sbf.format(d);

        return Long.parseLong(date+sequenceId);
    }

    private void reducePoint(Order order) {

        Double point = userMapper.queryPoint(order.getUserId());
        if (point < order.getPrice()){
            throw new BizException(LACK_OF_POINT);
        }

        int num = userMapper.reducePoint(order.getUserId(),order.getPrice());
        if (num < 1){
            throw new BizException(LACK_OF_POINT);
        }

    }
}
