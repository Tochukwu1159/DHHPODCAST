package com.netshiftdigital.dhhpodcast.exceptions;

public class ForbiddenRequestException extends RuntimeException{
    public ForbiddenRequestException(String message){
        super(message);
    }
}
