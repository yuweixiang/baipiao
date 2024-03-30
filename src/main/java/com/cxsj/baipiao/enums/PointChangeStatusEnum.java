package com.cxsj.baipiao.enums;

import lombok.Getter;

@Getter
public enum PointChangeStatusEnum {


    INIT("INIT", "未兑换"),

    REDEMMED("REDEMMED", "已兑换"),
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
    private PointChangeStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param resultCode 结果码
     * @return 服务结果枚举
     */
    public static PointChangeStatusEnum getByCode(String resultCode) {
        for (PointChangeStatusEnum type : values()) {
            if (type.getCode().equals(resultCode)) {
                return type;
            }
        }
        return null;
    }
}
