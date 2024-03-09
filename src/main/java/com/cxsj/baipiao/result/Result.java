package com.cxsj.baipiao.result;


public class Result<T> {
    private T data;
    private boolean success;
    private String resultCode;
    private String resultMsg;

    public Result(T data,Boolean success){
        this.data = data;
        this.success = success;
    }

    public Result(){};

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
