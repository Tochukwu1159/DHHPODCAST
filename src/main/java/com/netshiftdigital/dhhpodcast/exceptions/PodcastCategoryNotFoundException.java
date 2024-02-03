package com.netshiftdigital.dhhpodcast.exceptions;

public class PodcastCategoryNotFoundException extends RuntimeException{
    public PodcastCategoryNotFoundException(String message){
        super(message);
    }
}
