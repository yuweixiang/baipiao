package com.cxsj.baipiao.request;

import com.cxsj.baipiao.bizShare.BaseRequest;
import lombok.Data;

import java.util.Map;

@Data
public class PageRequest extends BaseRequest {

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private Long totalSize;

    private Map<String,String> map;
}
