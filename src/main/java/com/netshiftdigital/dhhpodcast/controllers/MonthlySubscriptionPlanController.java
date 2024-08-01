package com.netshiftdigital.dhhpodcast.controllers;

import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.SubscriptionPlanResponse;
import com.netshiftdigital.dhhpodcast.service.MonthlySubscriptionPlanService;
import com.netshiftdigital.dhhpodcast.service.SubscriptionPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subcription-group")
public class MonthlySubscriptionPlanController {

    @Autowired
    private MonthlySubscriptionPlanService subscriptionPlanService;

    @PostMapping
    public ResponseEntity<SubscriptionPlanResponse> createSubscriptionPlan(@RequestBody @Valid  SubscriptionPlanRequest request) {
        SubscriptionPlanResponse response = subscriptionPlanService.createMonthlySubscriptionPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionPlanResponse> updateSubscriptionPlan(@PathVariable
                                                                               Long id, @RequestBody @Valid SubscriptionPlanRequest request) {
        SubscriptionPlanResponse response = subscriptionPlanService.updateMonthlySubscriptionPlan(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionPlanResponse>> findAllSubscriptionPlans() {
        List<SubscriptionPlanResponse> response = subscriptionPlanService.findAllMonthlySubscriptionPlans();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanResponse> findSubscriptionPlanById(@PathVariable Long id) {
        SubscriptionPlanResponse response = subscriptionPlanService.findMonthlySubscriptionPlanById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Long id) {
        subscriptionPlanService.deleteMonthlySubscriptionPlan(id);
        return ResponseEntity.noContent().build();
    }
}
