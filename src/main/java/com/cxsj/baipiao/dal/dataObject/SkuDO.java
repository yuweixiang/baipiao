package com.cxsj.baipiao.dal.dataObject;

import lombok.Data;

@Data
public class SkuDO {

    private Long id;

    private String skuImage;

    private Double price;

    private Long stock;

    private Long soldNum;

    private String skuSpec;
}
