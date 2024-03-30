package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PointChange {

    private String redeemCode;

    private Date gmtCreate;

    private Date gmtModified;

    private Double point;

    private String changeStatus;

    private Date changeTime;

    private String changeUser;

    private String openid;
}
