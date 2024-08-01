package com.netshiftdigital.dhhpodcast.payloads.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AllUserResponse {
    private String firstName;
    //    @NotEmpty
//    @Size(min = 3, message = "Last name can not be less than 3")
    private String lastName;

    private String phoneNumber;
    //    @NotEmpty
//    @Size(min = 3, message = "Last name can not be less than 3")
    private String email;
    //    @NotEmpty
}
