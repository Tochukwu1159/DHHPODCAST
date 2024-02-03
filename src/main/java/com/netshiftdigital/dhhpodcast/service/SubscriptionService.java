package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.models.Subscription;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;

public interface SubscriptionService {
    Subscription subscribe(SubscriptionPlan plan);
    Subscription getSubscription(String email);
}