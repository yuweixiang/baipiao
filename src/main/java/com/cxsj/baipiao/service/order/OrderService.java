package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.request.OrderRenderReqeust;
import com.cxsj.baipiao.result.Result;
import org.springframework.stereotype.Service;

public interface OrderService {

    Result<Long> createOrder(OrderRenderReqeust reqeust);

    Order orderRender(OrderRenderReqeust reqeust);
}
