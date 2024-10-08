package com.netshiftdigital.dhhpodcast.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistedToken extends BaseEntity {

    private String token;
}
