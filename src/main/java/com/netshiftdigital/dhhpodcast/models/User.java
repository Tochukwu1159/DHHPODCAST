package com.netshiftdigital.dhhpodcast.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
@Builder
public class User extends BaseEntity{

    private String firstName;

    private String lastName;
    @Email
    private String email;

    private String password;
    @Enumerated(value = EnumType.STRING)
    private Roles roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;
    private Boolean isVerified;






}
