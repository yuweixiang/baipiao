package com.cxsj.baipiao.dal.dataObject;

import lombok.Data;

import java.util.Date;

@Data
public class OrderAddressDO {

    private String id;

    private Date gmtCreate;

    private Date gmtModified;

    private String city;

    private String area;

    private String town;

    private String detail;

    private String zipCode;
}
