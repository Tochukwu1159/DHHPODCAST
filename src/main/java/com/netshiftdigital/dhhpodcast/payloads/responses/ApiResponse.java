package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String message;
    private boolean status = false;
    private LocalDateTime timeCreated;
    private T data;
    private T error;

//    public ApiResponse(String message, boolean status, T data) {
//        this.message = message;
//        this.status = status;
//        this.data = data;
//    }
//
//    public ApiResponse(String message, boolean status) {
//        this.message = message;
//        this.status =status;
//    }

   public static <T> ApiResponse<T> success(T data){
   ApiResponse<T> response = new ApiResponse<>();
   response.setMessage("success");
   response.setStatus(true);
   response.setData(data);
   response.setTimeCreated(LocalDateTime.now());
        return  response;
   }

    public static <T> ApiResponse<T> error(T error){
        ApiResponse<T> response = new ApiResponse<>();
        response.setMessage("failure");
        response.setError(error);
        response.setStatus(false);
        response.setTimeCreated(LocalDateTime.now());
        return  response;
    }

}
