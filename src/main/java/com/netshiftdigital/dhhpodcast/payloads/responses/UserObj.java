package com.netshiftdigital.dhhpodcast.payloads.responses;

import com.netshiftdigital.dhhpodcast.utils.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserObj {
    private String firstName;
    private String lastName;

    private String email;

    private Roles role;
}
