package com.cxsj.baipiao.request;

import lombok.Data;

@Data
public class OrderQueryRequest extends PageRequest {

    private Long userId;

    private String status;
}
