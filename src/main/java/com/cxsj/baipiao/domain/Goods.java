package com.cxsj.baipiao.domain;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Goods {

    private Long id;

    private Long orderId;

    private String title;

    private Double price;

    private List<String> goodsDesc;

    private String primaryImage;

    private List<GoodsSpec> specList;

    private List<Sku> skuList;

    private String skuInfo;

    private Integer num;

    private Long categoryId;

    private Integer stockNum;

    private List<String> imageList;

    private String images;

    private Long soldNum;

    private String descImage;

    private Date gmtCreate;

    private Date gmtModified;

    private String feature;

    private Long goodsId;

    private Long skuId;

    public static void main(String[] args) {
        List<GoodsSpec> skuSpecList = new ArrayList<>();
        GoodsSpec skuSpec = new GoodsSpec();
        skuSpec.setTitle("颜色");
        skuSpec.setSpecId(10011L);
        skuSpec.setSpecValueList(buildSpecValueList());
        System.out.println(JSONObject.toJSONString(skuSpec.getSpecValueList()));
        GoodsSpec skuSpec1 = new GoodsSpec();
        skuSpec1.setTitle("尺码");
        skuSpec1.setSpecId(10013L);
        skuSpec1.setSpecValueList(buildSpecValueList1());
        skuSpecList.add(skuSpec);
        skuSpecList.add(skuSpec1);
        System.out.println(JSONObject.toJSONString(skuSpec1.getSpecValueList()));
        System.out.println(JSONObject.toJSONString(skuSpecList));
    }

    private static List<GoodsSpec.SpecValue> buildSpecValueList1() {
        GoodsSpec.SpecValue specValue = new GoodsSpec.SpecValue();
        specValue.setSpecValueId(11014L);
        specValue.setSpecValue("S");
        GoodsSpec.SpecValue specValue1 = new GoodsSpec.SpecValue();
        specValue1.setSpecValueId(11015L);
        specValue1.setSpecValue("S");

        GoodsSpec.SpecValue specValue2 = new GoodsSpec.SpecValue();
        specValue2.setSpecValueId(11016L);
        specValue2.setSpecValue("S");
        return Lists.newArrayList(specValue, specValue1, specValue2);
    }

    private static List<GoodsSpec.SpecValue> buildSpecValueList() {
        GoodsSpec.SpecValue specValue = new GoodsSpec.SpecValue();
        specValue.setSpecValueId(10012L);
        specValue.setSpecValue("米色荷叶边");
        return Lists.newArrayList(specValue);
    }
}
