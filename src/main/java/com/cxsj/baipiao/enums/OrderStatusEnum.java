package com.cxsj.baipiao.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    CREATED("CREATED", "已创建"),

    PAID("PAID", "待发货"),

    SHIPPED("SHIPPED", "待收货"),

    COMPLETE("COMPLETE", "已完成"),

    CANCELED("CANCELED", "已取消"),
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
    private OrderStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param resultCode 结果码
     * @return 服务结果枚举
     */
    public static OrderStatusEnum getByCode(String resultCode) {
        for (OrderStatusEnum type : values()) {
            if (type.getCode().equals(resultCode)) {
                return type;
            }
        }
        return null;
    }

}
