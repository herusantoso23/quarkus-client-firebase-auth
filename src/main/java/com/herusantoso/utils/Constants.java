package com.herusantoso.utils;

public class Constants {

    private Constants(){}

    public static final class ErrorCode {
        private ErrorCode(){}
        /* 400 */
        public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
        public static final String BAD_REQUEST = "BAD_REQUEST";
        /* 401 */
        public static final String UNAUTHORIZED = "UNAUTHORIZED";
        public static final String TOKEN_INVALID = "TOKEN_INVALID";
        /* 500 */
        public static final String INTERNAL_SERVER_ERROR = "UNAUTHORIZED";
        /* ForbiddenException */
        public static final String FORBIDDEN = "FORBIDDEN";
    }

}
