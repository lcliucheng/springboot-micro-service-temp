package com.lc.exception;


/**
 * 请求异常
 *
 * @author liucheng
 * @since 2018-08-23
 */
public class HttpException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpException(String msg) {
        super(msg);
    }

    public HttpException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public HttpException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public HttpException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}