package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Goods {

    private Long id;

    private Long orderId;

    private String title;

    private Double price;

    private String goodsDesc;

    private String primaryImage;

    private List<Sku> skuList;

    private String skuInfo;

    private Integer num;

    private Long categoryId;

    private Integer stockNum;

    private List<String> imageList;

    private String images;

    private Long soldNum;

    private String descImage;

    private Date gmtCreate;

    private Date gmtModified;

    private String feature;
}
