package com.netshiftdigital.dhhpodcast.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "subscription")
    @Entity
    @Builder
    public class Subscription {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Long id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
        @ManyToOne
        @JsonIgnore
        @JoinColumn(name = "podcast_id")
        private Podcast podcast;
        private String subscriptionCode;
        private String emailToken;

        private BigDecimal amount;
        private LocalDateTime nextPaymentDate;
        private String status;

        private long plan;
}



