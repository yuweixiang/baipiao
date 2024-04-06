package com.cxsj.baipiao.integration;

import com.cxsj.baipiao.diamond.DynamicConfig;
import com.jushuitan.api.ApiClient;
import com.jushuitan.api.ApiRequest;
import com.jushuitan.api.ApiResponse;
import com.jushuitan.api.DefaultApiClient;
import com.jushuitan.api.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JstWrapper {

    @Value("${jstAppKey}")
    private String appKey;

    @Value("${jstAppSecret}")
    private String appSecret;

    public String requestJst(String url,String biz) {
        // 实例化client
        ApiClient client = new DefaultApiClient();
        // 设置超时时间
//        ((DefaultApiClient)client).setReadTimeout(3000);
//        ((DefaultApiClient)client).setConnectTimeout(2000);
        String accessToken = DynamicConfig.getInstance().getIndexConfig().getAccessToken();
        // 构建请求对象
        ApiRequest request = new ApiRequest.Builder(url, appKey, appSecret)
                .biz(biz).build();

        // 执行接口调用
        try {
            ApiResponse response = client.execute(request, accessToken);
            log.info("is success: " + response.isSuccess());
            log.info("body: " + response.getBody());
            return response.getBody();
        } catch (ApiException e) {
            log.info("jst error: " ,e);
        } catch (Exception e) {
            // exception handle
            log.info("jst error: " ,e);
        }
        return null;
    }
}
