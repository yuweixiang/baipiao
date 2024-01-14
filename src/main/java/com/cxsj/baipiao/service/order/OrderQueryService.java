package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.request.OrderQueryRequest;

import java.util.List;

public interface OrderQueryService {

    List<Order> queryOrder(OrderQueryRequest request);

    Order queryOrderDetail(Long userId,Long orderId);
}
