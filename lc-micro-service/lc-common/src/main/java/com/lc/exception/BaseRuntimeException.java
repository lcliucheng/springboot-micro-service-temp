package com.lc.exception;

/**
 * 基础异常
 *
 * @author liucheng
 * @since 2018-08-23
 */
public class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误
     */
    private ErrorInfo errorInfo;

    public BaseRuntimeException(String msg) {
        super(msg);
        this.errorInfo = ErrorInfo.builder().code(ErrorCode.SYSTEM_ERROR.getValue()).msg(msg).build();
    }

    public BaseRuntimeException(Integer code, String msg) {
        super(msg);
        this.errorInfo = ErrorInfo.builder().code(code).msg(msg).build();
    }

    public BaseRuntimeException(ErrorInfo errorInfo) {
        super(errorInfo.getMsg());
        this.errorInfo = errorInfo;
    }

    public BaseRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
        this.errorInfo = ErrorInfo.builder().code(ErrorCode.SYSTEM_ERROR.getValue()).msg(msg).build();
    }

    public BaseRuntimeException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.getMsg(), cause);
        this.errorInfo = errorInfo;
    }

    /**
     * 获取错误
     *
     * @return 错误
     */
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
