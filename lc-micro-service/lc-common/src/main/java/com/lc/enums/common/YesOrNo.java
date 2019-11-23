package com.lc.enums.common;

import java.util.Objects;

/**
 * 是否枚举类
 *
 * @author Tortoise
 * @since 2019-04-13
 */
public enum YesOrNo {

    /* 否 */
    NO(0, "否"),

    /* 是 */
    YES(1, "是");

    private Integer value;
    private String desc;

    YesOrNo(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    /**
     * 获取枚举对象
     *
     * @param value 具体值
     * @return 枚举对象
     */
    public static YesOrNo parse(Integer value) {
        Objects.requireNonNull(value, "The matching value cannot be empty");

        for (YesOrNo object : values()) {
            if (value.equals(object.getValue())) {
                return object;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }

    /**
     * 获取枚举对象的具体值
     *
     * @return 具体值
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 获取枚举对象的描述
     *
     * @return 具体值
     */
    public String getDesc() {
        return desc;
    }
}
