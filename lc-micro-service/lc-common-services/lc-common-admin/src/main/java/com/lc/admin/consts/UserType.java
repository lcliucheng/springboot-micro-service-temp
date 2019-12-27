package com.lc.admin.consts;


/**
 * 渠道用户
 *
 * @author liucheng
 * @date  2019-12-31
 */
public enum UserType {
    /* 平台用户 */
    SYS("platform","平台用户"),

    /* 商务 */
    BUSI("busi","商务用户"),

    /* 渠道用户 */
    CHANNEL("channel","渠道用户"),

    ;

    private String value;

    private String name;

    UserType(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取枚举对象
     *
     * @param value 具体值
     * @return 枚举对象
     */
    public static String getValue(String value) {
        for (UserType object : values()) {
            if (value.equals(object.getValue())) {
                return object.getName();
            }
        }
        return "";
    }
}