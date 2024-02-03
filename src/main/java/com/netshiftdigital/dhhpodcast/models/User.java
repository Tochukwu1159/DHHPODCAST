package com.netshiftdigital.dhhpodcast.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

        private String firstName;

    private String lastName;

    private String email;

    private String password;
    @Enumerated(value = EnumType.STRING)
    private Roles roles;

    private String phoneNumber;
    private Boolean isVerified;


    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Likes> likes;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Comment> comments;


    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    private SubscriptionPlans subscriptionPlan;

    @OneToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;




}
