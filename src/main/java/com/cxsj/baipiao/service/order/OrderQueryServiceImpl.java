package com.cxsj.baipiao.service.order;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.bizShare.BizTemplate;
import com.cxsj.baipiao.dal.dao.*;
import com.cxsj.baipiao.domain.*;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.OrderQueryRequest;
import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.cxsj.baipiao.enums.ResultCodeEnum.ILLEGAL_ARGUMENT;

@Service
public class OrderQueryServiceImpl extends BizTemplate implements OrderQueryService{

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @Resource
    private OrderAddressMapper orderAddressMapper;

    @Override
    public PageResult<Order> queryOrderList(OrderQueryRequest request) {
        PageResult<Order> result = new PageResult(Lists.newArrayList(),false);
        processPageWithNoTransation(request,result,"orderList",()->{

            if (StringUtils.equals(request.getStatus(),"ALL")){
                request.setStatus("");
            }
            String openid = getOpenidByToken();
            User user = userMapper.queryByOpenid(openid);

            Integer totalCount = orderMapper.countOrdersByStatus(user.getId(),request.getStatus());
            if (totalCount == null || totalCount == 0){
                result.setTotalCount(0);
                result.setPageSize(request.getPageSize());
                result.setPageIndex(request.getPageIndex());
                buildSuccess(result);
                return;
            }
            result.setTotalCount(totalCount);
            List<Order> orders = orderMapper.queryOrdersByStatus(user.getId(),request.getStatus(),
                    (request.getPageIndex()-1)*request.getPageSize(),request.getPageSize());

            List<Long> orderIds = orders.stream().map(Order::getId).collect(Collectors.toList());
            List<Goods> goodsList = orderGoodsMapper.batchQuery(orderIds);
            goodsList.forEach(goods -> {
                goods.setSpecList(JSONObject.parseArray(goods.getSkuInfo(), GoodsSpec.class));
            });
            Map<Long,Goods> orderGoodsMap = goodsList.stream().
                    collect(Collectors.toMap(Goods::getOrderId, Function.identity()));

            orders.forEach(order->{
                OrderStatusEnum statusEnum = OrderStatusEnum.getByCode(order.getStatus());
                order.setStatusName(statusEnum.getDesc());
                order.setOrderGoods(orderGoodsMap.get(order.getId()));
            });
            result.setData(orders);
            buildSuccess(result);

        });
        return result;
    }

    @Override
    public Result<Order> queryOrderDetail(OrderQueryRequest request) {
        Result<Order> result = new Result<Order>();
        processWithNoTransation(request,result,"queryDetail",()->{

            if (request.getOrderId() == null) {
                throw new BizException(ILLEGAL_ARGUMENT, "订单id不能为空！");
            }

            String openid = getOpenidByToken();
            User user = userMapper.queryByOpenid(openid);

            Order order = orderMapper.queryById(user.getId(), request.getOrderId());
            OrderStatusEnum statusEnum = OrderStatusEnum.getByCode(order.getStatus());
            order.setStatusName(statusEnum.getDesc());
            Goods orderGoods = orderGoodsMapper.queryByOrder(request.getOrderId());
            orderGoods.setSpecList(JSONObject.parseArray(orderGoods.getSkuInfo(), GoodsSpec.class));
            Address orderAddress = orderAddressMapper.queryByOrder(request.getOrderId());

            order.setOrderGoods(orderGoods);
            order.setCreateTime(order.getGmtCreate().getTime());
            order.setOrderAddress(orderAddress);
            result.setData(order);
            buildSuccess(result);
        });
        return result;
    }

    @Override
    public List<UserOrder> queryUserOrderCount(Long userId) {

        return orderMapper.queryUserOrderCount(userId);
    }
}
