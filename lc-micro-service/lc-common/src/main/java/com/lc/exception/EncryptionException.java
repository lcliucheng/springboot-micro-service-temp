package com.lc.exception;

/**
 * <p> 加密异常 </p>
 *
 * @author liucheng
 * @since 2018-08-23
 */
public class EncryptionException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    public EncryptionException(String msg) {
        super(msg);
    }

    public EncryptionException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public EncryptionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public EncryptionException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }

}
