package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.List;

@Data
public class GoodsSpec {
    private Long specId;
    private Long id;

    private String title;

    private String sepcTitle;

    private List<GoodsSpec.SpecValue> specValueList;

    private String specValue;

    @Data
    public static class SpecValue{

        private Long specValueId;

        private String specValue;

        private String image;
    }
}
