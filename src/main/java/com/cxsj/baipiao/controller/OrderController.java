package com.cxsj.baipiao.controller;

import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.dal.dao.OrderGoodsMapper;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.domain.Sku;
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

        Long orderId = orderService.createOrder(reqeust);
        return new Result<>(orderId, true);
    }

    @RequestMapping("/render")
    public Result<Order> render(OrderRenderReqeust reqeust) {

        Goods gooods = new Goods();
        gooods.setId(reqeust.getGoodsId());
        Sku sku = new Sku();
        sku.setId(reqeust.getSkuId());
        gooods.setSkuList(Lists.newArrayList(sku));
        gooods.setNum(reqeust.getSkuNum());
        Order order = orderService.orderRender(reqeust.getUserId(), gooods);
        return new Result<>(order, true);
    }

    @RequestMapping("/queryOrderList")
    public PageResult<Order> queryOrderList(OrderQueryRequest reqeust) {

        validateRequest(reqeust);

        List<Order> orderList = orderQueryService.queryOrder(reqeust);

        return new PageResult<>(orderList, true);
    }

    @RequestMapping("/queryOrderDetail")
    public Result<Order> queryOrderDetail(OrderQueryRequest reqeust) {

        validateRequest(reqeust);
        if (reqeust.getOrderId() == null) {
            throw new BizException(ILLEGAL_ARGUMENT, "订单id不能为空！");
        }

        Order order = orderQueryService.queryOrderDetail(reqeust.getUserId(), reqeust.getOrderId());

        return new Result<>(order, true);
    }


    private void validateRequest(OrderQueryRequest reqeust) {
        if (reqeust.getUserId() == null) {
            throw new BizException(ILLEGAL_ARGUMENT, "用户id不能为空！");
        }
    }


}
