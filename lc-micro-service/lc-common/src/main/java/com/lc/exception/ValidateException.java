package com.lc.exception;

/**
 * 验证异常
 *
 * @author liucheng
 * @since 2018-08-23
 */
public class ValidateException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public ValidateException(String msg) {
        super(msg);
    }

    public ValidateException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public ValidateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ValidateException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}