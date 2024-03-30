package com.cxsj.baipiao.request;

import lombok.Data;

@Data
public class OrderQueryRequest extends PageRequest {

    private String status;

    private Long orderId;
}
