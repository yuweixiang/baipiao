package com.cxsj.baipiao.request;

import lombok.Data;

@Data
public class GoodsQueryRequest extends PageRequest{

    private Long categoryId;

    private String goodsName;
}
