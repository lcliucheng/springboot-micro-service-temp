package com.lc.admin.consts;


/**
 * 卡片状态
 *
 * @author liucheng
 * @date  2019-12-29
 */
public enum CardStatus {

    /* 未激活 */
    INACTIVATED(0,"未激活"),

    /* 已激活 */
    ACTIVATED(1,"已激活"),

    /* 已过期 */
    expired(2,"已过期"),

    /* 续费停用 */
    RENEWAL_IS_DISABLED(3,"续费停用"),

    /* 中止权益 */
    DISCONTINUE(5,"中止权益"),
    ;

    private int value;

    private String name;

    CardStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
    public static String getValue(int value) {
        for (CardStatus object : values()) {
            if (value == object.getValue()) {
                return object.getName();
            }
        }
        return "";
    }
}


