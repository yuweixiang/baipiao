package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private Long createTime;

    private Address orderAddress;

    private Goods orderGoods;

    private Long userId;

    private String status;

    private String statusName;

    private String logisticsNo;

    private Double price;

    private String feature;

    private int settleType = 1;

    private Logistics logistics;

    private String outerId;

    private String logisticsCompany;
}
