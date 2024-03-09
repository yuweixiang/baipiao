package com.cxsj.baipiao.domain;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Sku {

    private Long id;

    private Long goodsId;

    private String skuImage;

    private Double price;

    private Integer stock;

    private List<SpecInfo> specInfo;

    private Integer soldNum;

    private String skuSpecs;

    private String feature;

    public static void main(String[] args) {
        SpecInfo specInfo = new SpecInfo();
        specInfo.setSpecId(1L);
        specInfo.setSpecValueId(10012L);
        SpecInfo specInfo1 = new SpecInfo();
        specInfo1.setSpecId(2L);
        specInfo1.setSpecValueId(11014L);
        System.out.println(JSONObject.toJSONString(Lists.newArrayList(specInfo,specInfo1)));

        SpecInfo specInfo2 = new SpecInfo();
        specInfo2.setSpecId(1L);
        specInfo2.setSpecValueId(10012L);
        SpecInfo specInfo3 = new SpecInfo();
        specInfo3.setSpecId(2L);
        specInfo3.setSpecValueId(10015L);
        System.out.println(JSONObject.toJSONString(Lists.newArrayList(specInfo3,specInfo2)));
    }
}
