/**
 *
 *  * Alipay.com Inc.
 *  * Copyright (c) 2004-2014 All Rights Reserved.
 *  
 */
package com.cxsj.baipiao.enums;

/**
 *  * 业务层结果码
 * 
 * <p>错误码的用途:用于显示给调用方处理结果<p>
 * 
 * @author wb-yuweixiang
 * @version $Id: MyResultCodeEnum.java, v 0.1 2014-7-24 下午5:05:37 wb-yuweixiang Exp $
 */
public enum ResultCodeEnum {

    /** 成功 */
    SUCCESS("200", "成功"),

    /** 系统错误 */
    SYSTEM_ERROR("100", "系统错误"),

    /** 参数有误 */
    ILLEGAL_ARGUMENT("103", "请求参数有误"),

    APP_KEY_NOT_EXISTS("104", "appKey不存在"),
    USER_NOT_RIGHT("105", "改账号不是通过你的链接登记的，无法查询！"),
    ILLEGAL_REQUEST("106", "非法请求！"),
    LACK_OF_POINT("107","积分不足,无法下单!"),
    LACK_OF_STOCK("110","库存不足!"),
    NICK_NOT_LOGIN("108","账号未登记，无法查询!"),
    TIME_OUT("109","已经超过查询时间!"),


    /**********************************************************************************ling********************************************************************************/
    URL_ERROR("USR_ERROR","商品链接不正确!"),
    NO_PERSSION("NO_PERSSION","您无权限操作!"),
    LOGIN_TIMEOUT("LOGIN_TIMEOUT","登陆超时,请重新登陆!"),
    NOT_LOGIN("110","登陆后使用更多功能!"),
    POINT_NOT_ENOUGH("POINT_NOT_ENOUGH","积分不足,无法下单!"),
    SHOP_NOT_IN_RELATION("SHOP_NOT_IN_RELATION","店铺未绑定!"),
    IP_NOT_PERSSION("IP_NOT_PERSSION","系统识别到您存在多ip登录，已自动停止该ip的使用，为防止封号，请正确使用您的账号!"),


    /**********************************************************************************USER********************************************************************************/

    /** 用户登录失败 */
    LOGIN_FAILD("104", "用户登录失败!"),

    USER_NOT_EXIST("105", "用户不存在!"),

    PASSWORD_WRONG("106", "密码错误!"),

    TOKEN_ERROR("101","token错误!"),

    TOKEN_PASSED("102","token过期!"),

    CREATE_USER_NO_PERMISSION("107","无创建用户的权限!"),

    NO_PERMISSION_CREATE("108","无法跨部门创建用户!"),

    CANNOT_DELETE_USER("109","无法跨部门删除用户"),

    CANNOT_QUERY("110","无法查询"),

    TIMES_OUT("111","次数已经用完！请充值后再使用"),


    /********************************************************************************audit********************************************************************************/

    ITEM_NOT_EXIST("201","审核条目不存在"),
    CANNOT_AUDIT_ITEM("202","无法审核该信息"),
    ITEM_NO_PERMISSION_AUDIT("203","无权限审核改信息"),
    ;



    /** 枚举代码 */
    private String code;

    /** 枚举描述 */
    private String desc;

    /**
     * Constructors
     * 
     * @param code 结果码
     * @param desc 结果描述
     */
    private ResultCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     * 
     * @param resultCode 结果码
     * @return 服务结果枚举
     */
    public static ResultCodeEnum getByCode(String resultCode) {
        for (ResultCodeEnum type : values()) {
            if (type.getCode().equals(resultCode)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     * 
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }
}
