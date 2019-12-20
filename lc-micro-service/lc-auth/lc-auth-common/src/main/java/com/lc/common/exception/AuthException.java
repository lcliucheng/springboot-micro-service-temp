package com.lc.common.exception;

import com.lc.exception.BaseException;
import com.lc.exception.ErrorInfo;

/**
 * 授权异常
 *
 * @author liucheng
 * @since 2019-11-23
 */
public class AuthException extends BaseException {

    private static final long serialVersionUID = 1L;

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}