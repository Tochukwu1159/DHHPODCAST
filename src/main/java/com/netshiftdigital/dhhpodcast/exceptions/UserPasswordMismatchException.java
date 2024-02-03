package com.netshiftdigital.dhhpodcast.exceptions;

public class UserPasswordMismatchException extends RuntimeException{
    public UserPasswordMismatchException(String message){
        super(message);
    }
}
