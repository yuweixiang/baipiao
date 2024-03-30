package com.cxsj.baipiao.controller;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.dal.dao.UserMapper;
import com.cxsj.baipiao.domain.Goods;
import com.cxsj.baipiao.domain.User;
import com.cxsj.baipiao.request.GoodsQueryRequest;
import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;
import com.cxsj.baipiao.utils.HttpClientTemplete;
import com.cxsj.baipiao.utils.HttpRequestItem;
import com.cxsj.baipiao.utils.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {

    @Value("${appid}")
    private String appid;

    @Value("${appSecret}")
    private String appSecret;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/login")
    public Result<User> login(String code) {

        Result<User> result = new Result<>();
        HttpClientTemplete httpClientTemplete = new HttpClientTemplete();

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        String openResult = httpClientTemplete.getMethod(new HttpRequestItem(url, null,null) {
            @Override
            public String buildResult(String result) {
                return result;
            }
        });
        if (StringUtils.isBlank(openResult)){
            result.setData(null);
            result.setSuccess(false);
            result.setResultCode("LOGIN_FAILED");
            result.setResultMsg("登陆失败，请重试！");
            return result;
        }

        JSONObject object = JSONObject.parseObject(openResult);
        String openid = object.getString("openid");
        String sessionKey = object.getString("session_key");
        User user = userMapper.queryByOpenid(openid);
        if (user == null){
            user = new User();
            user.setOpenid(openid);
            user.setStatus("1");
            user.setPoint(0d);
            user.setSessionKey(sessionKey);
            userMapper.insert(user);
        }
        user.setOpenid("");
        user.setSessionKey("");
        user.setToken(TokenManager.buildToken(openid,null));
        return new Result<>(user,true);
    }
}
