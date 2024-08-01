package com.netshiftdigital.dhhpodcast.exceptions;
public class UserLoggedOutException extends RuntimeException {
    public UserLoggedOutException(String message) {
        super(message);
    }
}