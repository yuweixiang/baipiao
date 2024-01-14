package com.cxsj.baipiao.request;

import lombok.Data;

@Data
public class OrderRenderReqeust {

    private Long userId;

    private Long goodsId;

    private Long skuId;
}
