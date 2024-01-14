package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private Address orderAddress;

    private Goods orderGoods;

    private Long userId;

    private String status;

    private String logisticsNo;

    private Double price;

    private String feature;
}
