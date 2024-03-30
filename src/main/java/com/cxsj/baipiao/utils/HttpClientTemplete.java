package com.cxsj.baipiao.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class HttpClientTemplete {

    protected static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTemplete.class);

    private static int WAIT_TIME = 5000;

    private static int retryTimes = 1;

    public <T> T getMethod(HttpRequestItem item) {

        for (int i=0;i<retryTimes;i++) {
            GetMethod getMethod = new GetMethod(item.getUrl());
            HttpClient client = new HttpClient();
            try {
                if (StringUtils.isNotBlank(item.getCookies())) {
                    getMethod.setRequestHeader("cookie", item.getCookies());
                }
                if (StringUtils.isNotBlank(item.getRefer())) {
                    getMethod.setRequestHeader("Referer", item.getRefer());
                }
                client.getParams().setCookiePolicy("compatibility");
                // 执行，返回状态码
                int statusCode = client.executeMethod(getMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    return item.buildResult(getMethod.getResponseBodyAsString());
                }
            } catch (Exception e) {
                LOGGER.error("http获取数据错误！e:", e);
            } finally {
                try {
                    getMethod.releaseConnection();
                    ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
//                 e.printStackTrace();
                }
            }
        }
        return null;
    }


    public <T> T getMethodWithTimeOut(HttpRequestItem item,Integer timeOut) {

        for (int i=0;i<retryTimes;i++) {
            GetMethod getMethod = new GetMethod(item.getUrl());
            HttpClient client = new HttpClient();
            try {
                if (StringUtils.isNotBlank(item.getCookies())) {
                    getMethod.setRequestHeader("cookie", item.getCookies());
                }
                if (StringUtils.isNotBlank(item.getRefer())) {
                    getMethod.setRequestHeader("Referer", item.getRefer());
                }
                client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
                client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
                client.getParams().setCookiePolicy("compatibility");
                // 执行，返回状态码
                int statusCode = client.executeMethod(getMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    return item.buildResult(getMethod.getResponseBodyAsString());
                }
            } catch (Exception e) {
                LOGGER.error("http获取数据错误！e:", e);
            } finally {
                try {
                    getMethod.releaseConnection();
                    ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
//                 e.printStackTrace();
                }
            }
        }
        return null;
    }

    public <T> T postMethod(HttpRequestItem item, NameValuePair[] data) {

        for (int i=0;i<retryTimes;i++) {
            PostMethod postMethod = new PostMethod(item.getUrl());
            HttpClient client = new HttpClient();
            try {
                if (StringUtils.isNotBlank(item.getCookies())) {
                    postMethod.setRequestHeader("cookie", item.getCookies());
                }
                if (StringUtils.isNotBlank(item.getRefer())) {
                    postMethod.setRequestHeader("Referer", item.getRefer());
                }
                client.getParams().setCookiePolicy("compatibility");
                if (data != null && data.length != 0) {
                    postMethod.setRequestBody(data);
                    postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
                }
                if (StringUtils.isNotBlank(item.getCsrfToken())) {
                    postMethod.setRequestHeader("X-CSRF-Token", item.getCsrfToken());
                    postMethod.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                    postMethod.setRequestHeader("Accept", "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript, */*; q=0.01");
                }
                // 执行，返回状态码
                int statusCode = client.executeMethod(postMethod);
                if (statusCode == HttpStatus.SC_OK) {
                    return item.buildResult(postMethod.getResponseBodyAsString());
                }
            } catch (Exception e) {
//            e.printStackTrace();
            } finally {
                try {
                    postMethod.releaseConnection();
                    ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                } catch (Exception e) {
//                 e.printStackTrace();
                }
            }
        }
        return null;
    }
}
