package com.cxsj.baipiao.request;

import lombok.Data;

import java.util.Map;

@Data
public class PageRequest {

    private Integer pageIndex;

    private Integer pageSize;

    private Long totalSize;

    private Map<String,String> map;
}
