package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.dal.dao.OrderGoodsMapper;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.request.OrderQueryRequest;
import com.cxsj.baipiao.request.OrderRenderReqeust;
import com.cxsj.baipiao.service.order.OrderQueryService;
import com.cxsj.baipiao.service.order.OrderService;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.cxsj.baipiao.enums.ResultCodeEnum.ILLEGAL_ARGUMENT;

@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderQueryService orderQueryService;

    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    @RequestMapping("/createOrder")
    public Result<Long> createOrder(OrderRenderReqeust reqeust) {
        return orderService.createOrder(reqeust);
    }

    @RequestMapping("/render")
    public Result<Order> render(OrderRenderReqeust reqeust) {

        Order order = orderService.orderRender(reqeust);
        return new Result<>(order, true);
    }

    @RequestMapping("/queryOrderList")
    public PageResult<Order> queryOrderList(OrderQueryRequest reqeust) {

        return orderQueryService.queryOrderList(reqeust);
    }

    @RequestMapping("/queryOrderDetail")
    public Result<Order> queryOrderDetail(OrderQueryRequest reqeust) {

        return orderQueryService.queryOrderDetail(reqeust);
    }
}
