//package com.netshiftdigital.dhhpodcast.service.impl;
//
//import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
//import com.netshiftdigital.dhhpodcast.models.Subscription;
//import com.netshiftdigital.dhhpodcast.models.User;
//import com.netshiftdigital.dhhpodcast.repositories.SubscriptionRepository;
//import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
//import com.netshiftdigital.dhhpodcast.service.SubscriptionService;
//import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class SubscriptionServiceImpl implements SubscriptionService {
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Subscription subscribe( SubscriptionPlan plan) {
//        String email = SecurityConfig.getAuthenticatedUserEmail();
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user == null) {
//            // Handle the case when the user is not found (you may want to throw an exception)
//            return null;
//        }
//        Subscription existingSubscription = subscriptionRepository.findByUser(user.get());
//        if (existingSubscription != null) {
//            existingSubscription.setPlan(plan);
//            return subscriptionRepository.save(existingSubscription);
//        } else {
//            Subscription newSubscription = new Subscription();
//            newSubscription.setUser(user.get());
//            newSubscription.setPlan(plan);
//            return subscriptionRepository.save(newSubscription);
//        }
//    }
//
//    public Subscription getSubscription(String email) {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user == null) {
//            // Handle the case when the user is not found (you may want to throw an exception)
//            return null;
//        }
//        return subscriptionRepository.findByUser(user.get());
//    }
//}
