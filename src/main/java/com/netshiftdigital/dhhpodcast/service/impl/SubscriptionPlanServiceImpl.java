package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.AuthenticationFailedException;
import com.netshiftdigital.dhhpodcast.exceptions.BadRequestException;
import com.netshiftdigital.dhhpodcast.exceptions.CustomInternalServerException;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.models.Subscription;
import com.netshiftdigital.dhhpodcast.models.SubscriptionPlans;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanDto;
import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlansRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.repositories.SubscriptionPlanRepository;
import com.netshiftdigital.dhhpodcast.repositories.SubscriptionRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.PayStackPaymentService;
import com.netshiftdigital.dhhpodcast.service.SubscriptionPlanService;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

// SubscriptionService.java
@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {
    private final SubscriptionRepository subscriptionRepository;

    @Value("${paystack.api.url}/subscription")
    private String paystackSubscriptionUrl;
    @Value("${paystack.api.url}/plan")
    private String paystackplanurl;

    @Value("${paystack.api.url}/subscription/{idOrCode}")
    private String paystackSubscriptionUrlSingle;

    @Value("${paystack.api.url}/plan/{idOrCode}")
    private String paystackPlanUrlSingle;


    private final  RestClient restClient;
    private final ModelMapper modelMapper;


    @Value("${paystack_secret_key}")  // Use the actual property key for your secret key
    private String paystackSecretKey;

    private final  SubscriptionPlanRepository subscriptionPlanRepository;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

@Override
    public SubscriptionPlans createSubscriptionPlan(SubscriptionPlanDto subscriptionPlan) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + paystackSecretKey);
//        headers.set("Content-Type", "application/json");

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", subscriptionPlan.getPlanName());
        requestBody.put("amount", subscriptionPlan.getAmount() * 100);
        requestBody.put("interval", subscriptionPlan.getInterval());

//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
      PayStackResponse payStackResponse = restClient.postData(PayStackResponse.class, requestBody, paystackplanurl);
    System.out.println(payStackResponse + "payStackResponse" );
//        ResponseEntity<PayStackResponse> response = restTemplate.postForEntity(paystackplanurl, requestEntity, PayStackResponse.class);
//        System.out.println(response + "   new HttpEntity<>(headers),");
//        if (response.getStatusCode().is2xxSuccessful()) {
//         PayStackResponse payStackResponse = response.getBody();
            SubscriptionPlans subscriptionPlans = new SubscriptionPlans();
            subscriptionPlans.setPaystackPlanCode(payStackResponse.getData().getPlanCode());
            subscriptionPlans.setPlanId(payStackResponse.getData().getId());
            subscriptionPlans.setCurrency(payStackResponse.getData().getCurrency());
            subscriptionPlans.setCreatedTime(LocalDateTime.now());
            subscriptionPlans.setAmount(subscriptionPlan.getAmount());
            subscriptionPlans.setName(subscriptionPlan.getPlanName());
            subscriptionPlans.setIntervals(subscriptionPlan.getInterval());
            return subscriptionPlanRepository.save(subscriptionPlans);
    }

    @Override
    public List<PayStackResponseObj> getAllSubscriptionPlan() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);

        ResponseEntity<PayStackResponseObj> responseEntity = restTemplate.exchange(
                paystackplanurl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                PayStackResponseObj.class
        );
        System.out.println(responseEntity + "  response");
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            PayStackResponseObj subscription = responseEntity.getBody();
            return Collections.singletonList(subscription);
        } else {
            System.out.println("Error response: " + responseEntity.getStatusCodeValue());
            return Collections.emptyList();
        }
    }

    @Override
    public PlanResponseDto getSubscriptionPlanByIdOrCode(String idOrCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);

        String apiUrl = paystackPlanUrlSingle.replace("{idOrCode}", idOrCode);
        System.out.println(apiUrl + "  api url");

        try {
            ResponseEntity<PlanResponseDto> responseEntity = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    PlanResponseDto.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                // Handle non-successful response, e.g., log the error
                System.err.println("Error response from Paystack API: " + responseEntity.getStatusCode());
                return null; // or throw an exception based on your error handling strategy
            }
        } catch (HttpClientErrorException e) {
            // Handle HTTP client errors (4xx)
            System.err.println("HTTP client error: " + e.getRawStatusCode() + " - " + e.getStatusText());
            // Handle or throw as needed
        } catch (HttpServerErrorException e) {
            // Handle HTTP server errors (5xx)
            System.err.println("HTTP server error: " + e.getRawStatusCode() + " - " + e.getStatusText());
            // Handle or throw as needed
        } catch (RestClientException e) {
            // Handle other REST client exceptions
            System.err.println("Rest client exception: " + e.getMessage());
            // Handle or throw as needed
        }

        return null; // or throw an exception if needed
    }


    public void updatePlan(String idOrCode, String updatedName) {

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request data
        String requestUrl = paystackplanurl + "/" + idOrCode;
        String requestData = "{ \"name\": \"" + updatedName + "\" }";
        System.out.println(requestUrl);
        System.out.println(requestData);

        // Create HttpEntity with headers and request data
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);

        // Make the PUT request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        // Check the response status
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Plan updated successfully");
        } else {
            System.out.println("Failed to update plan. Response: " + responseEntity.getBody());
        }
    }


    @Override
    public List<SubscriptionDto> getAllSubscriptions() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);

        ResponseEntity<SubscriptionDto> responseEntity = restTemplate.exchange(
                paystackSubscriptionUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                SubscriptionDto.class
        );
        System.out.println(responseEntity);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println(responseEntity + "https://api.paystack.co/subscription");
            SubscriptionDto subscription = responseEntity.getBody();
            return Collections.singletonList(subscription);
        } else {
            System.out.println("Error response: " + responseEntity.getStatusCodeValue());
            return Collections.emptyList();
        }
    }



    @Override
    public SingleSubscriptionDto getSubscriptionByIdOrCode(String idOrCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);

        String apiUrl = paystackSubscriptionUrlSingle.replace("{idOrCode}", idOrCode);
        System.out.println(apiUrl + "  api url");
        try {
        ResponseEntity<SingleSubscriptionDto> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                SingleSubscriptionDto.class
        );
//        return  restClient.getData(SingleSubscriptionDto.class, apiUrl);
            System.out.println(responseEntity);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            SingleSubscriptionDto subscriptionDto = responseEntity.getBody();

            return subscriptionDto;
        } else {
            // Handle non-OK status code, log, throw exception, etc.
            System.err.println("Error: " + responseEntity.getStatusCode());
        }
    } catch (HttpClientErrorException e) {
        // Handle exceptions (e.g., 4xx errors)
        System.err.println("HTTP Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
    } catch (Exception e) {
        // Handle general exceptions
        e.printStackTrace();
    }

    // Return null or handle appropriately based on your requirements
        return null;
}


    @Override
    public SubscriptionResponseDto initiateSubscription(String planId) {
        SubscriptionPlans subscriptionPlan = subscriptionPlanRepository.findByPaystackPlanCode(planId);
        System.out.println(subscriptionPlan + "  subscriptionPlan");
        SubscriptionPlansRequest entity = modelMapper.map(subscriptionPlan, SubscriptionPlansRequest.class);
        SubscriptionResponseDto responseDto = callPaystackApi(entity);

        return responseDto;
    }

    private SubscriptionResponseDto callPaystackApi(SubscriptionPlansRequest subscriptionPlan) {
        String email = SecurityConfig.getAuthenticatedUserEmail();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + paystackSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Create the request body for creating a subscription
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("plan", subscriptionPlan.getPaystackPlanCode());
        requestBody.put("customer", email);
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        System.out.println(requestEntity);
        try{
            // Make API call to Paystack to create subscription
            ResponseEntity<SubscriptionResponseDto> responseEntity = restTemplate.postForEntity(
                    paystackSubscriptionUrl,
                    requestEntity,
                    SubscriptionResponseDto.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                SubscriptionResponseDto responseDto = responseEntity.getBody();
                Optional<User> suscribedUser = userRepository.findByEmail(email);
                SubscriptionPlans suscribedPlan = subscriptionPlanRepository.findByPlanId(responseDto.getData().getPlan());
                System.out.println(suscribedPlan + "plan user");
                suscribedUser.get().setSubscriptionPlan(suscribedPlan);
                userRepository.save(suscribedUser.get());
                Subscription userSubscription = new Subscription();
                userSubscription.setUser(suscribedUser.get());
                userSubscription.setSubscriptionCode(responseDto.getData().getSubscriptionCode());
                userSubscription.setNextPaymentDate(responseDto.getData().getNextPaymentDate());
                userSubscription.setAmount(responseDto.getData().getAmount());
                userSubscription.setPlan(responseDto.getData().getPlan());
                userSubscription.setEmailToken(responseDto.getData().getEmailToken());
                userSubscription.setStatus(responseDto.getData().getStatus());
                subscriptionRepository.save(userSubscription);
                return responseDto;
            } else {
                throw new RuntimeException("Failed to create subscription. Paystack API returned an error.");
            }
        }catch (HttpClientErrorException.BadRequest e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw  new BadRequestException("This subscription is already in place");
        }catch (HttpClientErrorException.NotFound e){
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw  new ResourceNotFoundException("A customer with the specified email or code was not found");
        }catch (HttpClientErrorException.Unauthorized e){
            log.error(e.getMessage(), e);
            throw new AuthenticationFailedException("Invalid token");
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new CustomInternalServerException("Internal server error");
        }


    }


}
