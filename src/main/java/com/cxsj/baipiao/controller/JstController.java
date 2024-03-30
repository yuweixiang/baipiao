package com.cxsj.baipiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.dal.dao.JstTokenMapper;
import com.cxsj.baipiao.domain.JstToken;
import com.cxsj.baipiao.utils.HttpClientTemplete;
import com.cxsj.baipiao.utils.HttpRequestItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.NameValuePair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class JstController {

    @Resource
    private JstTokenMapper jstTokenMapper;

    @RequestMapping("/callback")
    public void getToken(String appKey,String code,String state,String sign){

        JstToken jstToken = new JstToken();
        jstToken.setAppKey(appKey);
        jstToken.setCode(code);
        jstToken.setState(state);
        jstToken.setSign(sign);
        HttpClientTemplete httpClientTemplete = new HttpClientTemplete();
        String url = "https://openapi.jushuitan.com/openWeb/auth/accessToken";
        NameValuePair[] pair = new NameValuePair[6];
        pair[0] = new NameValuePair("app_key",appKey);
        pair[1] = new NameValuePair("timestamp",String.valueOf(System.currentTimeMillis()));
        pair[2] = new NameValuePair("grant_type","authorization_code");
        pair[3] = new NameValuePair("charset","utf-8");
        pair[4] = new NameValuePair("code",code);
        pair[5] = new NameValuePair("sign",sign);

        String str = httpClientTemplete.postMethod(new HttpRequestItem(url,null,null) {
            @Override
            public String buildResult(String result) {
                return result;
            }
        }, pair);

        log.info("jst result:{}",str);
        JSONObject object = JSONObject.parseObject(str);
        jstToken.setAccessToken(object.getJSONObject("data").getString("access_token"));
        jstToken.setExpiresIn(object.getJSONObject("data").getLong("expires_in"));
        jstToken.setRefreshToken(object.getJSONObject("data").getString("refresh_token"));

        jstTokenMapper.insert(jstToken);
    }
}
