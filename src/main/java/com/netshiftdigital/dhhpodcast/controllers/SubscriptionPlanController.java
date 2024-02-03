package com.netshiftdigital.dhhpodcast.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netshiftdigital.dhhpodcast.models.SubscriptionPlans;
import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.service.SubscriptionPlanService;
import com.netshiftdigital.dhhpodcast.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/api/v1/subscription-plans")
    public class SubscriptionPlanController {

        private final SubscriptionPlanService subscriptionService;
    private final ObjectMapper objectMapper = new ObjectMapper();

        @PostMapping
        public ResponseEntity<SubscriptionPlans> createSubscriptionPlan(@RequestBody SubscriptionPlanDto subscriptionPlan) {
            SubscriptionPlans createdPlan = subscriptionService.createSubscriptionPlan(subscriptionPlan);
            return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
        }


    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptionsFromPaystack() {
        List<SubscriptionDto> subscriptions = subscriptionService.getAllSubscriptions();

        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
        }
    }
    @GetMapping("allPlan")
    public ResponseEntity<List<PayStackResponseObj>> getAllSubscriptionPlan(){
        List<PayStackResponseObj> subscriptions = subscriptionService.getAllSubscriptionPlan();

        if (subscriptions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(subscriptions, HttpStatus.OK);
        }
    }
    @GetMapping("/singlePlan/{idOrCode}")
    public ResponseEntity<PlanResponseDto> getSubscriptionPlanByIdOrCode(@PathVariable String idOrCode) {
        PlanResponseDto subscription = subscriptionService.getSubscriptionPlanByIdOrCode(idOrCode);

        if (subscription != null) {
            return new ResponseEntity<>(subscription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{idOrCode}")
    public void updatePlan(@PathVariable String idOrCode, @RequestParam String updatedName) {
        subscriptionService.updatePlan(idOrCode, updatedName);
    }

    @GetMapping("/{idOrCode}")
    public ResponseEntity<SingleSubscriptionDto> getSubscriptionByIdOrCode(@PathVariable String idOrCode) {
        SingleSubscriptionDto subscription = subscriptionService.getSubscriptionByIdOrCode(idOrCode);

        if (subscription != null) {
            return new ResponseEntity<>(subscription, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subscribe/{planId}")
    public ResponseEntity<SubscriptionResponseDto> initiateSubscription(@PathVariable String planId) {
        SubscriptionResponseDto responseDto = subscriptionService.initiateSubscription(planId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload) {
        try {
            // Parse JSON payload
            JsonNode payloadJson = objectMapper.readTree(payload);
            String event = payloadJson.get("event").asText();
            JsonNode eventData = payloadJson.get("data");

            if ("subscription.create".equals(event)) {
                handleSubscriptionCreate(eventData);
            } else if ("subscription.disable".equals(event)) {
                handleSubscriptionDisable(eventData);
            } else if ("other_event".equals(event)) {
                // Handle other events as needed
            }
            System.out.println("Received Paystack webhook event: " + event);
            System.out.println("Webhook data: " + eventData);
            return new ResponseEntity<>("Webhook received successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing webhook", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void handleSubscriptionCreate(JsonNode eventData) {
        String subscriptionCode = eventData.get("subscription_code").asText();
        String customerCode = eventData.get("customer").get("customer_code").asText();

        // Your logic to handle subscription creation
        System.out.println("Handling subscription create event...");
        System.out.println("Subscription Code: " + subscriptionCode);
        System.out.println("Customer Code: " + customerCode);

        // Update your system with the subscription information...
    }

    private void handleSubscriptionDisable(JsonNode eventData) {
        // Extract subscription details and update your system
        String subscriptionCode = eventData.get("subscription_code").asText();
        String customerCode = eventData.get("customer").get("customer_code").asText();

        // Your logic to handle subscription disable
        System.out.println("Handling subscription disable event...");
        System.out.println("Subscription Code: " + subscriptionCode);
        System.out.println("Customer Code: " + customerCode);

        // Update your system to reflect the disabled subscription...
    }
}
