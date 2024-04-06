package com.cxsj.baipiao.domain;

import lombok.Data;

import java.util.List;

@Data
public class IndexConfig {

    private List<String> bannerUrls;

    private List<IndexTab> tabList;

    private String accessToken;
}
