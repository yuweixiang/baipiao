package com.cxsj.baipiao.domain;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkuSpec {

    private Long id;

    private String title;

    private List<String> specValueList;

    private String specValues;

    public static void main(String[] args) {
        List<SkuSpec> list = new ArrayList<>();
        SkuSpec skuSpec = new SkuSpec();
        skuSpec.setTitle("颜色");
        skuSpec.setSpecValues(JSONObject.toJSONString(Lists.newArrayList("红色","白色","蓝色")));
        list.add(skuSpec);

        SkuSpec skuSpec1 = new SkuSpec();
        skuSpec1.setTitle("尺寸");
        skuSpec1.setSpecValues(JSONObject.toJSONString(Lists.newArrayList("M","L","XL","XXL")));
        list.add(skuSpec1);
        System.out.println(JSONObject.toJSON(list));
    }
}
