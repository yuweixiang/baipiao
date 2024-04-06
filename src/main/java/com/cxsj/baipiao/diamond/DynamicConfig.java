/**
 *
 */
package com.cxsj.baipiao.diamond;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cxsj.baipiao.domain.IndexConfig;
import com.cxsj.baipiao.domain.IndexTab;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.StringReader;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * diamond配置
 *
 * @author yuweixiang
 * @version $Id: DynamicConfig.java, v 0.1 2014-12-16 下午11:52:21 yuweixiang Exp $
 */
@Data
@Slf4j
public class DynamicConfig {

    private Map<String, String> map = new HashMap<>();

    private IndexConfig indexConfig = new IndexConfig();

    /** 配置 */
    private static final DynamicConfig dynamicConfig = new DynamicConfig();

    /** 监听 */
    private ManagerListener dynamicConfigListener = new ManagerListener() {
        public void receiveConfigInfo(String configInfo) {
            configInfo = StringUtils.strip(configInfo);
            if (StringUtils.isNotBlank(configInfo)) {

                log.warn("Change alipush-ps config: " + configInfo);
                Properties prop = new Properties();

                try {
                    prop.load(new StringReader(configInfo));
                    updateConfig(prop);

                } catch (Exception e) {
                    log.error("Parse alipush-ps config failed:" + configInfo, e);
                }
            }
        }

        public Executor getExecutor() {
            return null;
        }
    };

    /**
     * 更新数据
     *
     * @param properties
     * @throws Exception
     */
    private void updateConfig(Properties properties) throws Exception {
        indexConfig.setTabList(JSONObject.parseArray(getStr(properties.getProperty("tabList"),""), IndexTab.class));
        indexConfig.setBannerUrls(JSONObject.parseArray(getStr(properties.getProperty("bannerUrls"),""), String.class));
        indexConfig.setAccessToken(getStr(properties.getProperty("accessToken"),""));
    }

    private String getStr(String str, String defStr) {
        return StringUtils.isBlank(str) ? defStr : str;
    }

    /**
     * 构建开放平台jmode集合
     *
     * @param str diamond中设置的参数
     * @return 构建结果
     */
    @SuppressWarnings("unchecked")
    private Map<String, String> buildDymicMap(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return JSONArray.parseObject(str, Map.class);
    }

    /**
     * 构建可导入系统数据
     *
     * @param str 系统列表
     * @return 构造结果
     */
    private List<String> buildListByStr(String str) {

        if (StringUtils.isBlank(str)) {
            return new ArrayList<String>();
        }
        String[] strList = str.split(",");
        List<String> list = new ArrayList<String>(strList.length);
        for (int i = 0; i < strList.length; i++) {
            list.add(strList[i]);
        }
        return list;
    }

    /**
     * 获取boolean值
     *
     * @param strValue (T F)
     * @param defaultValue 默认值
     * @return 值
     */
    private boolean getBooleanValue(String strValue, boolean defaultValue) {
        if (null != strValue) {
            if (StringUtils.equalsIgnoreCase(strValue, "T")) {
                return true;
            } else {
                return false;
            }
        } else {
            return defaultValue;
        }
    }

    /**
     * 获取实例
     *
     * @return 返回diamon实例
     */
    public static DynamicConfig getInstance() {
        return dynamicConfig;
    }

    /**
     * 配置
     *
     */
    private DynamicConfig() {
        try {
            String groupId = "DEFAULT_GROUP";
            String dataId = "cxsj.baipiao.dynamic.config";
            DefaultDiamondManager defaultDiamondManager = new DefaultDiamondManager(groupId, dataId,
                    dynamicConfigListener);
            defaultDiamondManager.getDiamondConfigure().setPort(8080);
            String configInfo = defaultDiamondManager.getAvailableConfigureInfomation(3000);
            dynamicConfigListener.receiveConfigInfo(configInfo);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("CheckLoginStatus.java：" + e);
        }
    }

    /**
     * main函数
     *
     * @param args 输入参数
     */
    public static void main(String args[]) {
        System.out.println(DynamicConfig.getInstance().getIndexConfig());
    }
}
