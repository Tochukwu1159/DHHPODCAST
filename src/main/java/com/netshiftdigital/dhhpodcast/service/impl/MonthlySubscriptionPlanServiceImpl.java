package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.models.SubscriptionPlan;
import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.SubscriptionPlanResponse;
import com.netshiftdigital.dhhpodcast.repositories.SubscriptionPlanRepo;
import com.netshiftdigital.dhhpodcast.repositories.SubscriptionPlanRepository;
import com.netshiftdigital.dhhpodcast.service.MonthlySubscriptionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonthlySubscriptionPlanServiceImpl implements MonthlySubscriptionPlanService {

    @Autowired
    private SubscriptionPlanRepo subscriptionPlanRepository;

    @Override
    public SubscriptionPlanResponse createMonthlySubscriptionPlan(SubscriptionPlanRequest request) {
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setName(request.getName());
        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return mapToResponse(savedPlan);
    }

    @Override
    public SubscriptionPlanResponse updateMonthlySubscriptionPlan(Long id, SubscriptionPlanRequest request) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription plan not found with id: " + id));
        subscriptionPlan.setName(request.getName());
        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return mapToResponse(updatedPlan);
    }

    @Override
    public List<SubscriptionPlanResponse> findAllMonthlySubscriptionPlans() {
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findAll();
        return plans.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public SubscriptionPlanResponse findMonthlySubscriptionPlanById(Long id) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription plan not found with id: " + id));
        return mapToResponse(subscriptionPlan);
    }

    @Override
    public void deleteMonthlySubscriptionPlan(Long id) {
        subscriptionPlanRepository.deleteById(id);
    }

    private SubscriptionPlanResponse mapToResponse(SubscriptionPlan subscriptionPlan) {
        SubscriptionPlanResponse response = new SubscriptionPlanResponse();
        response.setId(subscriptionPlan.getId());
        response.setName(subscriptionPlan.getName());
        return response;
    }
}
