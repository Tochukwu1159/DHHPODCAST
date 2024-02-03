package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.exceptions.AuthenticationFailedException;
import com.netshiftdigital.dhhpodcast.exceptions.BadRequestException;
import com.netshiftdigital.dhhpodcast.exceptions.CustomInternalServerException;
import com.netshiftdigital.dhhpodcast.payloads.responses.PayStackResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.Subscription;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
@RequiredArgsConstructor
@Component
@Slf4j
public class RestClient {
    private final RestTemplate restTemplate;


    @Value("${paystack_secret_key}")  // Use the actual property key for your secret key
    private String paystackSecretKey;
    public  <T> T postData(Class<T> clazz, @Nullable Map<String, Object> request, String url){
        HttpEntity<?> httpEntity = buildPostHeaders(request);
        try{
            ResponseEntity<T> response = restTemplate.postForEntity(url, httpEntity, clazz);
            log.info("response {}",response );
            return response.getBody();
        }catch (HttpClientErrorException.BadRequest e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw  new BadRequestException("invalid data");
        }catch (HttpClientErrorException.Unauthorized e){
            log.error(e.getMessage(), e);
            throw new AuthenticationFailedException("Invalid token");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new CustomInternalServerException("Internal server error");
        }


    }

    public  <T> T getData(Class<T> clazz,  String url){
        HttpEntity<?> httpEntity = buildGetHeaders();
        try{
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET,httpEntity, clazz);
            log.info("response {}",response );
            return response.getBody();
        }catch (HttpClientErrorException.BadRequest e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw  new BadRequestException("invalid data");
        }catch (HttpClientErrorException.Unauthorized e){
            log.error(e.getMessage(), e);
            throw new AuthenticationFailedException("Invalid token");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new CustomInternalServerException("Internal server error");
        }



    }
    private HttpEntity<?> buildGetHeaders(){
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<?> buildPostHeaders(Map<String, Object> request){
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.set("Content-Type", "application/json");

        return  new HttpEntity<>(request, headers);



    }


}
