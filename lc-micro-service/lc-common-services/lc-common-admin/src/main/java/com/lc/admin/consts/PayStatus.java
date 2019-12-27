package com.lc.admin.consts;


/**
 * 支付状态
 *
 * @author liucheng
 * @date  2019-12-29
 */
public enum PayStatus {

    /* 未支付 */
    PAY_NOT(0,"未支付"),

    /* 已支付 */
    PAY_FINISHED(1,"已支付"),

    /* 支中付 */
    PAYING(2,"支付中"),

    /* 退款中 */
    REFUNDING(3, "退款中"),

    /* 退款失败 */
    REFUND_FALI(4, "退款失败"),

    /* 退款成功 */
    REFUND_SUCC(5, "退款成功"),
    ;

    private int value;

    private String name;

    PayStatus(int value,String name) {
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
        for (PayStatus object : values()) {
            if (value == object.getValue()) {
                return object.getName();
            }
        }
        return "";
    }
}