package com.netshiftdigital.dhhpodcast.service.impl;


import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.*;
import com.netshiftdigital.dhhpodcast.models.*;
import com.netshiftdigital.dhhpodcast.models.Subscription;
import com.netshiftdigital.dhhpodcast.payloads.requests.*;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.payloads.responses.PayStackTransactionResponse;
import com.netshiftdigital.dhhpodcast.repositories.*;
import com.netshiftdigital.dhhpodcast.service.EmailService;
import com.netshiftdigital.dhhpodcast.service.PayStackPaymentService;
import com.netshiftdigital.dhhpodcast.service.SubscriptionPlanService;
import com.netshiftdigital.dhhpodcast.utils.Constants;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.naming.AuthenticationException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

// SubscriptionService.java
@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {
    private final UserProfileRepository userProfileRepository;
    private final EmailService emailService;
    private final SubscriptionRepository subscriptionRepository;

    @Value("${paystack.api.url}/subscription")
    private String paystackSubscriptionUrl;
    @Value("${paystack.api.url}/plan")
    private String paystackplanurl;
    @Value("${paystack.api.transaction.url}/initialize")
    private String paystacktransactionurl;


    @Value("${paystack.api.url}/subscription/{idOrCode}")
    private String paystackSubscriptionUrlSingle;

    @Value("${paystack.api.url}/plan/{idOrCode}")
    private String paystackPlanUrlSingle;


    private final RestClient restClient;
    private final ModelMapper modelMapper;


    @Value("${paystack_secret_key}")  // Use the actual property key for your secret key
    private String paystackSecretKey;

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepo subscriptionPlanRepo;

    @Override
    public SubscriptionPlans createSubscriptionPlan(SubscriptionPlanDto subscriptionPlan) {
        Optional<com.netshiftdigital.dhhpodcast.models.SubscriptionPlan> existingPlan = subscriptionPlanRepo.findById(subscriptionPlan.getPlanId());
        if (existingPlan == null) {
            throw new PodcastCategoryNotFoundException("Subscription group with Id not found: " + subscriptionPlan.getPlanId());
        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + paystackSecretKey);
//        headers.set("Content-Type", "application/json");

        // Prepare request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", existingPlan.get().getName());
        requestBody.put("amount", subscriptionPlan.getAmount() * 100);
        requestBody.put("interval", subscriptionPlan.getInterval());

//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        PayStackResponse payStackResponse = restClient.postData(PayStackResponse.class, requestBody, paystackplanurl);
        System.out.println(payStackResponse + "payStackResponse");
//        ResponseEntity<PayStackResponse> response = restTemplate.postForEntity(paystackplanurl, requestEntity, PayStackResponse.class);
//        System.out.println(response + "   new HttpEntity<>(headers),");
//        if (response.getStatusCode().is2xxSuccessful()) {
//         PayStackResponse payStackResponse = response.getBody();
        SubscriptionPlans subscriptionPlans = new SubscriptionPlans();
        subscriptionPlans.setPaystackPlanCode(payStackResponse.getData().getPlanCode());
        subscriptionPlans.setPlanId(payStackResponse.getData().getId());
        subscriptionPlans.setCurrency(payStackResponse.getData().getCurrency());
        subscriptionPlans.setAmount(subscriptionPlan.getAmount());
        subscriptionPlans.setName(existingPlan.get().getName());
        subscriptionPlans.setIntervals(subscriptionPlan.getInterval());
        return subscriptionPlanRepository.save(subscriptionPlans);
    }

    public PayStackTransactionResponse initTransaction(PayStackTransactionRequestDto request) throws Exception {
        try {
            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("email", request.getEmail());
            requestBody.put("amount", request.getAmount() * 100);


            // Consuming PayStack API using HttpClient
            PayStackAuthorizationResponse payStackResponse = restClient.postData(PayStackAuthorizationResponse.class, requestBody, paystacktransactionurl);

            if (payStackResponse.isStatus()) {
                return handleSuccessfulResponse(payStackResponse);
            } else {
                handleErrorResponse(payStackResponse);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Failure initializing PayStack transaction");
        }
        return null;

    }

    private PayStackTransactionResponse handleSuccessfulResponse(PayStackAuthorizationResponse response) {
        return new PayStackTransactionResponse(
                response.getMessage(), response.getData().getAuthorizationUrl(), response.getData().getReference(), response.getData().getAccessCode());
    }

    private void handleErrorResponse(PayStackAuthorizationResponse response) throws Exception {
        boolean statusCode = response.isStatus();
        if (!statusCode) {
            throw new AuthenticationException("Unauthorized request to PayStack API");
        } else {
            throw new Exception("Error occurred while initializing PayStack transaction. HTTP Status Code: " + statusCode);
        }
    }


    public PayStackVerification verifyTransaction(String reference) throws Exception {
        try {
            // Consuming PayStack API using HttpClient
            PayStackVerification payStackResponse = restClient.getData(
                    PayStackVerification.class,
                    "https://api.paystack.co/transaction/verify/" + reference
            );
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + paystackSecretKey);
//            String apiUrl = "https://api.paystack.co/transaction/verify/" + reference;
//            ResponseEntity<PayStackVerification> responseEntity = restTemplate.exchange(
//                    apiUrl,
//                    HttpMethod.GET,
//                    new HttpEntity<>(headers),
//                    PayStackVerification.class
//            );

            if (payStackResponse.isStatus()) {
                return payStackResponse;
            } else {
                handleVerificationErrorResponse(payStackResponse);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Error occurred while verifying PayStack transaction");
        }
        return null;
    }


    private void handleVerificationErrorResponse(PayStackVerification response) throws Exception {
        throw new Exception(
                String.format("Error occurred while verifying PayStack transaction. Status: %s", response.isStatus())
        );
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
        return null;
    }


    @Override
    public SubscriptionResponseDto initiateSubscription(String planId) {
        SubscriptionPlans subscriptionPlan = subscriptionPlanRepository.findByPaystackPlanCode(planId);
        System.out.println(subscriptionPlan + "  subscriptionPlan");
        SubscriptionPlansRequest entity = modelMapper.map(subscriptionPlan, SubscriptionPlansRequest.class);
        SubscriptionResponseDto responseDto = callPayStackApi(entity);

        return responseDto;
    }

    private SubscriptionResponseDto callPayStackApi(SubscriptionPlansRequest subscriptionPlan) {
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
        try {
            // Make API call to Paystack to create subscription
            ResponseEntity<SubscriptionResponseDto> responseEntity = restTemplate.postForEntity(
                    paystackSubscriptionUrl,
                    requestEntity,
                    SubscriptionResponseDto.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                SubscriptionResponseDto responseDto = responseEntity.getBody();
                Optional<User> suscribedUser = userRepository.findByEmail(email);
                Profile profile = userProfileRepository.findByUser(suscribedUser.get());

                SubscriptionPlans suscribedPlan = subscriptionPlanRepository.findByPlanId(responseDto.getData().getPlan());

                Subscription userSubscription = new Subscription();
                userSubscription.setProfile(profile);
                userSubscription.setSubscriptionCode(responseDto.getData().getSubscriptionCode());
                userSubscription.setSubscriptionPlan(suscribedPlan.getName());
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
        } catch (HttpClientErrorException.BadRequest e) {
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw new BadRequestException("This subscription is already in place");
        } catch (HttpClientErrorException.NotFound e) {
            log.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw new ResourceNotFoundException("A customer with the specified email or code was not found");
        } catch (HttpClientErrorException.Unauthorized e) {
            log.error(e.getMessage(), e);
            throw new AuthenticationFailedException("Invalid token");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CustomInternalServerException("Internal server error");
        }


    }


    @Scheduled(cron = "0 0 8 * * ?") // Run at 8 AM every day
    public void sendSubscriptionReminders() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Subscription subscription : subscriptions) {
            LocalDate nextPaymentDate = subscription.getNextPaymentDate().toLocalDate();
            long daysUntilExpiration = ChronoUnit.DAYS.between(today, nextPaymentDate);

            if (daysUntilExpiration == 5 || daysUntilExpiration == 7 || daysUntilExpiration == 3 || daysUntilExpiration == 0) {
                String subject = "Subscription Expiration Reminder";
                String message = "Your subscription is expiring in " + daysUntilExpiration + " days.";

                // Check if user and email exist
                String email = subscription.getProfile() != null && subscription.getProfile().getUser() != null
                        ? subscription.getProfile().getUser().getEmail()
                        : null;

                if (email != null) {
                    Map<String, Object> model = new HashMap<>();
                    model.put("expiration", message);

                    EmailDetails emailDetails = EmailDetails.builder()
                            .recipient(email)
                            .subject(subject)
                            .templateName("email-template")
                            .model(model)
                            .build();

                    try {
                        emailService.sendHtmlEmail(emailDetails);
                    } catch (Exception e) {
                        // Log the exception instead of throwing
                        System.err.println("Failed to send the email to " + email + ": " + e.getMessage());
                    }
                }

                if (daysUntilExpiration <= 0) {
                    subscription.setSubscriptionPlan("BASIC");
                }
            }
        }
        subscriptionRepository.saveAll(subscriptions);
    }

}

