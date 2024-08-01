package com.netshiftdigital.dhhpodcast.exceptions;
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
