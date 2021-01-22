package com.herusantoso.handlers.exception;

public class TokenInvalidException extends SecurityException {
    public TokenInvalidException() {
    }

    public TokenInvalidException(String errorMessage) {
        this(errorMessage, (Throwable)null);
    }

    public TokenInvalidException(Throwable cause) {
        this((String)null, cause);
    }

    public TokenInvalidException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
