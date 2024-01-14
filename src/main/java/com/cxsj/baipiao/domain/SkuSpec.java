package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.List;

@Data
public class SkuSpec {

    private Long id;

    private String title;

    private List<String> specValueList;

    private String specValues;
}
