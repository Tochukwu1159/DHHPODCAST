package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.models.SubscriptionPlans;
import com.netshiftdigital.dhhpodcast.payloads.requests.PayStackTransactionRequestDto;
import com.netshiftdigital.dhhpodcast.payloads.requests.SubscriptionPlanDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;

import java.util.List;

public interface SubscriptionPlanService {
    SubscriptionPlans createSubscriptionPlan(SubscriptionPlanDto subscriptionPlan);
    List<SubscriptionDto> getAllSubscriptions();
    PayStackTransactionResponse initTransaction(PayStackTransactionRequestDto request) throws Exception;
    SingleSubscriptionDto getSubscriptionByIdOrCode(String idOrCode);
    SubscriptionResponseDto initiateSubscription(String planId);
    PlanResponseDto getSubscriptionPlanByIdOrCode(String idOrCode);
     void updatePlan(String idOrCode, String updatedName);
    List<PayStackResponseObj> getAllSubscriptionPlan();}
