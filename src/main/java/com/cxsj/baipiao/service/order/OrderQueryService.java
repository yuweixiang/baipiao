package com.cxsj.baipiao.service.order;

import com.cxsj.baipiao.domain.Order;
import com.cxsj.baipiao.request.OrderQueryRequest;
import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;

import java.util.List;

public interface OrderQueryService {

    PageResult<Order> queryOrderList(OrderQueryRequest request);

    Result<Order> queryOrderDetail(OrderQueryRequest reqeust);
}
