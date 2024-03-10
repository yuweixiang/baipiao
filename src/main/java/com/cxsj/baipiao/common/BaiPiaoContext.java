package com.cxsj.baipiao.common;

import lombok.Data;

import java.util.Map;

@Data
public class BaiPiaoContext {

    private String actionType;

    private String totalCount;

    private Map<String,String> map;
}
