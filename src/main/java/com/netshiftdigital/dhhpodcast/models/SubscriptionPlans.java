package com.netshiftdigital.dhhpodcast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscriptionPlans")
@Entity
@Builder
public class SubscriptionPlans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private Double amount;
    private String intervals;
    private String currency;
    private String paystackPlanCode;
    private LocalDateTime createdTime;

    private Long planId;

    @OneToMany(mappedBy = "subscriptionPlan", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<User> users;

}