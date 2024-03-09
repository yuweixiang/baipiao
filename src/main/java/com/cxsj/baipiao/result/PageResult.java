package com.cxsj.baipiao.result;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> data;
    private boolean success;
    private String resultCode;
    private String resultMsg;

    private Integer pageIndex;
    private Integer pageSize;

    private Integer totalCount;

    public PageResult(List<T> data,Boolean success){
        this.data = data;
        this.success = success;
    }

    public PageResult(){
    }
}
