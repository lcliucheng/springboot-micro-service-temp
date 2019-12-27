package com.lc.admin.consts;

/**
 * 提现码导入状态
 *
 * @author liucheng
 * @date 2019/7/22
 */
public enum WithdrawStatus {

    /** 未领取 */
    UNRECEIVE("未领取"),

    /** 已兑换 */
    RECEIVED("已兑换") ,

    /** 已过期 */
    EXPIRED("已过期") ,
    ;

    private String msg;

    WithdrawStatus(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
