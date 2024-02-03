package com.netshiftdigital.dhhpodcast.exceptions;
public class CustomInternalServerException extends RuntimeException{
    public CustomInternalServerException(String message){
        super(message);
    }
}