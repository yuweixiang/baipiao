package com.cxsj.baipiao.utils;

import lombok.Data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class HttpRequestItem {

    private String url;

    private String cookies;

    private String refer;
    private String csrfToken;

    private boolean image;

    Map<String,String> map = new HashMap<>();
    private String address;

    public HttpRequestItem(String url, String cookies, String refer){
        this.url = url;
        this.cookies = cookies;
        this.refer = refer;
    }

    public HttpRequestItem(String url, String cookies, String refer, boolean image){
        this.url = url;
        this.cookies = cookies;
        this.refer = refer;
        this.image = image;
    }
    public HttpRequestItem(String url, String cookies, String refer, String address){
        this.url = url;
        this.cookies = cookies;
        this.refer = refer;
        this.address = address;
    }
    public HttpRequestItem(String url, String cookies, String refer, Map<String,String> map){
        this.url = url;
        this.cookies = cookies;
        this.refer = refer;
        this.address = address;
        this.map=map;
    }

    public abstract <T> T buildResult(String result);
    public <T> T buildInputStreamResult(InputStream result){
        return null;
    }

    public void putValue(String key,String value){
        map.put(key,value);
    }
}
