package br.ufmg.dcc.saracura.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

}
