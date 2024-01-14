package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import org.springframework.stereotype.Service;

public interface OrderService {

    Long createOrder(Order order);

    Order orderRender(Long userId, Goods goods);
}