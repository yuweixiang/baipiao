package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.request.OrderRenderReqeust;
import org.springframework.stereotype.Service;

public interface OrderService {

    Long createOrder(OrderRenderReqeust reqeust);

    Order orderRender(OrderRenderReqeust reqeust);
}
