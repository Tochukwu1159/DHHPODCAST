package com.netshiftdigital.dhhpodcast.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscriptionPlan")
@Entity
@Builder
public class SubscriptionPlan extends BaseEntity{
    private String name;}