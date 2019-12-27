package com.lc.admin.consts;

/**
 * @author xieze
 * @date 2019/12/17 15:08
 */
public enum OrderStatsOrderTypeEnum {

    ONE("1", "", "权益首次购买"),
    TWO("2", "", "权益续费"),
    THIRD("3", "", "旅游开团支付"),
    FOUR("4", "", "码上有礼支付"),
    FIVE("5", "", "参团支付"),
    SIX("6", "", "权益兑换领取权益"),
    SEVEN_ONE("7", "1", "普通零售"),
    SEVEN_TWO("7", "2", "会盒卡"),
    SEVEN_FOUR("7", "4", "拼团"),
    SEVEN_FIVE("7", "5", "会盒卡折扣权益"),
    SEVEN_SIX("7", "6", "会盒卡免费权益"),
    EIGHT("8", "", "会盒秒杀"),
    ;

    private String orderType;
    private String secOrderType;
    private String label;

    OrderStatsOrderTypeEnum(String orderType, String secOrderType, String label){
        this.orderType = orderType;
        this.secOrderType = secOrderType;
        this.label = label;
    }


    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSecOrderType() {
        return secOrderType;
    }

    public void setSecOrderType(String secOrderType) {
        this.secOrderType = secOrderType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static String getLabel(String orderType, String secOrderType) {
        for (OrderStatsOrderTypeEnum object : values()) {
            if ("7".equals(orderType) && object.getOrderType().equals(orderType) && object.getSecOrderType().equals(secOrderType)) {
                return object.getLabel();
            }
            if (object.getOrderType().equals(orderType)){
                return object.getLabel();
            }
        }
        return "";
    }
}
