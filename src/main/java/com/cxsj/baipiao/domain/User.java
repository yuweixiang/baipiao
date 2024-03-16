package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String telephone;

    private String nick;

    private String pic;

    private String status;

    private Double point;

    private List<Address> addresses;

    private CustomerService serviceInfo;

    private List<UserOrder> userOrders;

    private String feature;

    private String sex;

    private String openid;

    private String sessionKey;

    private String token;
}
