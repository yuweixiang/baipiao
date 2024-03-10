package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Address {

    private Long id;

    private Long orderId;

    private Long userId;

    private Date gmtCreate;

    private Date gmtModified;

    private String isDefault;

    private String province;

    private String provinceCode;

    private String city;

    private String cityCode;

    private String areaCode;

    private String area;

    private String town;

    private String detail;

    private String receiveName;

    private String telephone;

    private String label;

    private String feature;
}
