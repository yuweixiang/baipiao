package com.cxsj.baipiao.dal.dataObject;

import com.cxsj.baipiao.domain.Address;
import com.cxsj.baipiao.domain.Goods;
import lombok.Data;

import java.util.Date;

@Data
public class OrderDO {

    private String id;

    private Date gmtCreate;

    private Date gmtModified;

    private Long userId;

    private String status;

    private Double price;
}
