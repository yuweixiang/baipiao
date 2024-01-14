package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.dal.dao.GoodsMapper;
import com.cxsj.baipiao.dal.dao.OrderAddressMapper;
import com.cxsj.baipiao.dal.dao.OrderGoodsMapper;
import com.cxsj.baipiao.dal.dao.OrderMapper;
import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.request.OrderQueryRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderQueryServiceImpl implements OrderQueryService{

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    @Override
    public List<Order> queryOrder(OrderQueryRequest request) {

        List<Order> orders = orderMapper.queryOrdersByStatus(request.getUserId(),request.getStatus(),
                request.getPageIndex()*request.getPageSize(),request.getPageSize());

        List<Long> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());
        List<Goods> goods = orderGoodsMapper.batchQuery(orderIds);
        Map<Long,Goods> orderGoodsMap = goods.stream().
                collect(Collectors.toMap(Goods::getOrderId, Function.identity()));

        orders.forEach(order->{
            order.setOrderGoods(orderGoodsMap.get(order.getId()));
        });
        return orders;
    }

    @Override
    public Order queryOrderDetail(Long userId,Long orderId) {

        Order order = orderMapper.queryById(userId,orderId);

        Goods orderGoods = orderGoodsMapper.queryByOrder(orderId);

        Address orderAddress = orderAddressMapper.queryByOrder(orderId);

        order.setOrderGoods(orderGoods);
        order.setOrderAddress(orderAddress);

        return order;
    }
}
