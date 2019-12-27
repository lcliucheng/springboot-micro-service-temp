package com.lc.admin.consts;


/**
 * 支付渠道
 *
 * @author liucheng
 * @date  2019-12-31
 */
public enum PayChannel {
    /* 微信支付 */
    WX("WX","微信"),

    /* 支付宝支付 */
    ALIPAY("AP","支付宝"),

    ;

    private String value;

    private String name;

    PayChannel(String value, String name) {
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
        for (PayChannel object : values()) {
            if (value.equals(object.getValue())) {
                return object.getName();
            }
        }
        return "";
    }
}