package com.lc.exception;


/**
 * 业务异常
 *
 * @author liucheng
 * @since 2018-08-23
 */
public class ServiceException extends BaseException {

    private static final long serialVersionUID = 1L;

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ServiceException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}