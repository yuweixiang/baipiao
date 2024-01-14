package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

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

    private String feature;
}
