package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Address {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String isDefault;

    private String province;

    private String city;

    private String area;

    private String town;

    private String detail;

    private String zipCode;
}
