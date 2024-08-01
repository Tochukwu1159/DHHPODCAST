package com.netshiftdigital.dhhpodcast.payloads.requests;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordWithoutToken {
    @NotNull(message = "Email  is required")
    @NotEmpty
    private String email;
    @NotNull(message = "New password  is required")
    @NotEmpty
    private String newPassword;
    @NotNull(message = "Confirm new password is required")
    @NotEmpty
    private String confirmPassword;



}