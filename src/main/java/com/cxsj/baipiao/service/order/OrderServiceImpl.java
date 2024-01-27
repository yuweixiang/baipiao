package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.dal.dao.*;
import com.cxsj.baipiao.domain.*;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.OrderRenderReqeust;
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
    private SequenceMapper sequenceMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public Long createOrder(OrderRenderReqeust reqeust) {

        Long orderId = generateOrderId();

        Sku sku = skuMapper.queryById(reqeust.getSkuId());

        if (sku.getStock() < reqeust.getSkuNum()){
            throw new BizException(ResultCodeEnum.LACK_OF_STOCK,"库存不足！请选择其他商品下单！");
        }

        Order order = new Order();
        order.setOrderGoods(buildOrderGoods(sku,reqeust.getSkuNum()));
        order.setUserId(reqeust.getUserId());
        order.setPrice(sku.getPrice() * reqeust.getSkuNum() );
        order.setOrderAddress(addressMapper.queryById(reqeust.getAddressId()));
        reducePoint(order);
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setId(orderId);
        orderMapper.insert(order);
        order.getOrderAddress().setOrderId(orderId);
        order.getOrderGoods().setOrderId(orderId);
        orderAddressMapper.insert(order.getOrderAddress());
        orderGoodsMapper.insert(order.getOrderGoods());


        return order.getId();
    }

    private Goods buildOrderGoods(Sku sku,int num) {
        Goods goods = goodsMapper.queryById(sku.getGoodsId());
        goods.setId(sku.getGoodsId());
        goods.setPrice(sku.getPrice());
        goods.setNum(num);
        goods.setPrimaryImage(sku.getSkuImage());
        goods.setGoodsDesc(sku.getSkuImage());
        goods.setSkuInfo(sku.getSkuSpecs());
        return goods;
    }

    public Order orderRender(Long userId, Goods goods){

        Address address = addressMapper.queryDefaultAddress(userId);

        Goods renderGoods = goodsMapper.queryById(goods.getId());
        Sku sku = skuMapper.queryById(goods.getSkuList().get(0).getId());
        renderGoods.setSkuList(Lists.newArrayList(sku));
        Order order = new Order();
        order.setOrderGoods(renderGoods);
        order.setOrderAddress(address);
        order.setPrice(sku.getPrice() * goods.getNum());
        return order;
    }

    private Long generateOrderId() {
        Long sequenceId = sequenceMapper.nextValue("order_id_seq");

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
