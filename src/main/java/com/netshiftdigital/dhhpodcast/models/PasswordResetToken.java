package com.netshiftdigital.dhhpodcast.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Table(name = "password_reset_tokens")
@Entity
@Setter
@Getter
public class PasswordResetToken extends BaseEntity {
    private String token;
    @OneToOne
    @JoinColumn(name = "users_details_users_id")
    private User usersDetails;

}


