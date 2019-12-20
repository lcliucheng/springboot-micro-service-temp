package com.lc.exception;

/**
 * 处理异常
 *
 * @author liucheng
 * @since 2018-08-23
 */
public class HandleException extends BaseException {

    private static final long serialVersionUID = 1L;

    public HandleException(String msg) {
        super(msg);
    }

    public HandleException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public HandleException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public HandleException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}