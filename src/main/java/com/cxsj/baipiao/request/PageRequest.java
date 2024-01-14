package com.cxsj.baipiao.request;

import lombok.Data;

import java.util.Map;

@Data
public class PageRequest {

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private Long totalSize;

    private Map<String,String> map;
}
