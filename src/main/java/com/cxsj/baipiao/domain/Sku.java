package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.List;

@Data
public class Sku {

    private Long id;

    private Long goodsId;

    private String skuImage;

    private Double price;

    private Integer stock;

    private Integer soldNum;

    private List<SkuSpec> skuSpecList;

    private String skuSpecs;

    private String feature;
}
