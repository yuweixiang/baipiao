package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.Date;

@Data
public class JstToken {

    private Long id;

    private String appKey;

    private String code;

    private String sign;

    private String state;

    private String accessToken;

    private Date gmtCreate;

    private Long expiresIn;

    private String refreshToken;
}
