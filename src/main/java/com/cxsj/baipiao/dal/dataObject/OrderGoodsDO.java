package com.cxsj.baipiao.dal.dataObject;

import com.cxsj.baipiao.domain.Sku;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderGoodsDO {

    private Long id;

    private String title;

    private String price;

    private String desc;

    private String primaryImage;

    private Long stockNum;

    private Long soldNum;

    private Date gmtCreate;

    private Date gmtModified;
}
