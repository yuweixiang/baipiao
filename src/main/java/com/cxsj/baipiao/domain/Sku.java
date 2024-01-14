package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.List;

@Data
public class Sku {

    private Long id;

    private String skuImage;

    private Double price;

    private Long stock;

    private Long soldNum;

    private List<SkuSpec> skuSpecList;

    private String skuSpecs;

    private String feature;
}
