package com.netshiftdigital.dhhpodcast.repositories;

import com.netshiftdigital.dhhpodcast.models.SubscriptionPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlans, Long> {

    SubscriptionPlans findByPaystackPlanCode(String planCode);
    SubscriptionPlans findByPlanId(long planId);
}
