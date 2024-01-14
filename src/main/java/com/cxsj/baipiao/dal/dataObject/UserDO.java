package com.cxsj.baipiao.dal.dataObject;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String telephone;

    private String nick;

    private String status;

    private String point;
}
