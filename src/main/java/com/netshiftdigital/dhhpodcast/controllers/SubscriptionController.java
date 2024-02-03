//package com.netshiftdigital.dhhpodcast.controllers;
//
//import com.netshiftdigital.dhhpodcast.models.Subscription;
//import com.netshiftdigital.dhhpodcast.service.SubscriptionService;
//import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/v1/subscription")
//public class SubscriptionController {
//    @Autowired
//    private SubscriptionService subscriptionService;
//
//    @PostMapping("/subscribe")
//    public Subscription subscribe( @RequestParam SubscriptionPlan plan) {
//        return subscriptionService.subscribe(plan);
//    }
//
//    @GetMapping("/getSubscription")
//    public Subscription getSubscription(@RequestParam String email) {
//        return subscriptionService.getSubscription(email);
//    }
//}
