package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.SubscriptionPlanResponse;

import java.util.List;

public interface MonthlySubscriptionPlanService {
    SubscriptionPlanResponse createMonthlySubscriptionPlan(SubscriptionPlanRequest request);
    SubscriptionPlanResponse updateMonthlySubscriptionPlan(Long id, SubscriptionPlanRequest request);
    List<SubscriptionPlanResponse> findAllMonthlySubscriptionPlans();
    SubscriptionPlanResponse findMonthlySubscriptionPlanById(Long id);
    void deleteMonthlySubscriptionPlan(Long id);
}
