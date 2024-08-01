package com.netshiftdigital.dhhpodcast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netshiftdigital.dhhpodcast.models.Subscription;
import com.netshiftdigital.dhhpodcast.models.SubscriptionPlans;
import com.netshiftdigital.dhhpodcast.payloads.requests.PaystackWebhookPayload;
import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanDto;
import com.netshiftdigital.dhhpodcast.repositories.SubscriptionRepository;
import com.netshiftdigital.dhhpodcast.service.SubscriptionPlanService;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
    @RequestMapping("/api/v1/podcast/subscription-plans/")
    public class SubscriptionPlanController {

        private final SubscriptionPlanService subscriptionService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SubscriptionRepository subscriptionRepository;

    @PostMapping
        public ResponseEntity<SubscriptionPlans> createSubscriptionPlan(@RequestBody @Valid SubscriptionPlanDto subscriptionPlan) {
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
    public void updatePlan(@PathVariable String idOrCode, @RequestParam @Valid String updatedName) {
        subscriptionService.updatePlan(idOrCode, updatedName);
    }

    @GetMapping("/{idOrCode}")
    public ResponseEntity<SingleSubscriptionDto> getSubscriptionByIdOrCode(@PathVariable @Valid String idOrCode) {
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


    @PostMapping("/paystack-webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody @Valid PaystackWebhookPayload payload) {
        try {
            String event = payload.getEvent();

            if ("subscription.create".equals(event)) {
                handleSubscriptionEvent(payload);
            } else if ("subscription.disable".equals(event)) {
                handleSubscriptionDisableEvent(payload);
            }  else if ("invoice".equals(payload.getEvent())) {
            handleInvoiceEvent(payload);
        } else if ("customer".equals(payload.getEvent())) {
            handleCustomerEvent(payload);
        } else if ("payment".equals(payload.getEvent())) {
            handlePaymentEvent(payload);
        } else if ("transfer".equals(payload.getEvent())) {
            handleTransferEvent(payload);
        } else if ("refund".equals(payload.getEvent())) {
                handleRefundEvent(payload);
            }

            System.out.println("Received Paystack webhook event: " + event);
            System.out.println("Webhook data: " + payload);
            return new ResponseEntity<>("Webhook received successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error processing webhook", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleSubscriptionEvent(PaystackWebhookPayload payload) {
        String subscriptionCode = payload.getData().getSubscriptionCode();
        String newStatus = payload.getData().getStatus();

        // Update your local database with the new subscription status
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findBySubscriptionCode(subscriptionCode);

        if (subscriptionOptional.isPresent()) {
            Subscription subscription = subscriptionOptional.get();
            subscription.setStatus(newStatus);
            subscriptionRepository.save(subscription);

            System.out.println("Subscription status updated: " + subscription);
        } else {
            System.out.println("Subscription not found for code: " + subscriptionCode);
        }
    }

    private void handleSubscriptionDisableEvent(PaystackWebhookPayload payload) {
        // Extract relevant information from the payload
        String subscriptionCode = payload.getData().getSubscriptionCode();
        String newStatus = payload.getData().getStatus();

        // Update your local database with the new subscription status
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findBySubscriptionCode(subscriptionCode);

        if (subscriptionOptional.isPresent()) {
            Subscription subscription = subscriptionOptional.get();
            subscription.setStatus("disabled");
            subscriptionRepository.save(subscription);

            System.out.println("Subscription status updated for disable event: " + subscription);
        } else {
            System.out.println("Subscription not found for code: " + subscriptionCode);
        }
    }


    private void handleSubscriptionEnableEvent(PaystackWebhookPayload payload) {
        // Implement logic for subscription enable event
    }

    private void handleInvoiceEvent(PaystackWebhookPayload payload) {
        // Implement logic for invoice event
    }

    private void handleCustomerEvent(PaystackWebhookPayload payload) {
        // Implement logic for customer event
    }

    private void handlePaymentEvent(PaystackWebhookPayload payload) {
        // Implement logic for payment event
    }

    private void handleTransferEvent(PaystackWebhookPayload payload) {
        // Implement logic for transfer event
    }

    private void handleRefundEvent(PaystackWebhookPayload payload) {
        // Implement logic for refund event
    }

    private void handleOtherEvent(PaystackWebhookPayload payload) {
        // Implement logic for other event types
    }
}
