package com.netshiftdigital.dhhpodcast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "subscription")
    @Entity
    @Builder
    public class Subscription extends BaseEntity {
        @OneToOne
        @JsonIgnore
        @JoinColumn(name = "profile_id", nullable = false)
        private Profile profile;
        private String subscriptionCode;
        private String emailToken;

        private String subscriptionPlan;

        private BigDecimal amount;
        private LocalDateTime nextPaymentDate;
        private String status;

        private long plan;

}



