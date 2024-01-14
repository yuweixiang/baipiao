package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.Result;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.enums.OrderStatusEnum;
import com.cxsj.baipiao.service.order.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
@RequestMapping("order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @RequestMapping("/createOrder")
    public Result<Long> createOrder(){
        Order order = new Order();
        order.setPrice(10d);
        order.setUserId(123L);
        order.setStatus(OrderStatusEnum.PAID.getCode());
        Long orderId = orderService.createOrder(order);
        return new Result<>(orderId,true);
    }
}
