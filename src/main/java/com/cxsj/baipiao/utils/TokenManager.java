package com.cxsj.baipiao.utils;

import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenManager {

    private static String header = "{\"typ\":\"JWT\",\"alg\":\"HS256\"}";

    protected static final String USER_NAME = "userName";
    protected static final String ROLE = "role";

    public static String getUser(String token) {
        try {
            token = URLDecoder.decode(token,"utf-8");
            String[] strs = token.split("\\.");
            if (strs.length < 3) {
                throw new BizException(ResultCodeEnum.TOKEN_ERROR, "token错误!");
            }
            String jsonStr = new String(new BASE64Decoder().decodeBuffer(strs[1]));
            Map<String, String> map = JSONObject.parseObject(jsonStr, Map.class);
            if (new Date().getTime() - Long.parseLong(map.get("time")) > 60 * 60 * 1000 * 24) {
                throw new BizException(ResultCodeEnum.TOKEN_PASSED, "token已⃣过期");
            }
            String userName = map.get("userName");
            if (!StringUtils.equals(buildToken(userName, map.get("time")), token)) {
                throw new BizException(ResultCodeEnum.TOKEN_ERROR, "token错误!");
            }
            return userName;
        } catch (Exception e) {
            throw new BizException(ResultCodeEnum.TOKEN_ERROR, "token错误!");
        }
    }


    public static String buildToken(String userName, String time) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        if (StringUtils.isBlank(time)) {
            map.put("time", String.valueOf(new Date().getTime()));
        } else {
            map.put("time", time);
        }
        String encodedString = new BASE64Encoder().encode(header.getBytes()) + "."
                + new BASE64Encoder()
                .encode(JSONObject.toJSONString(map).getBytes());
        String encryptStr = HMACSHA256(encodedString.getBytes(), "luqinglaowang".getBytes());
        return encodedString + "." + encryptStr;
    }


    public static String HMACSHA256(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }
}
