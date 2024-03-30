package com.cxsj.baipiao.request;

import com.cxsj.baipiao.bizShare.BaseRequest;
import lombok.Data;

@Data
public class OrderRenderReqeust extends BaseRequest {

    private Long goodsId;

    private Long skuId;

    private Integer skuNum;

    private Long addressId;
}
