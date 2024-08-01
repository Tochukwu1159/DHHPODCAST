package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.models.ConfirmationToken;
import com.netshiftdigital.dhhpodcast.payloads.requests.LoginRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.ResetPasswordWithoutToken;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.payloads.requests.UserRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService
{
    UserResponse createAdmin(UserRequestDto userRequest);
    UserResponse createUser(UserRequestDto userRequest);

    LoginResponse loginUser(LoginRequest loginRequest);

    String verifyUser(String userToken);
    ConfirmationToken generateNewToken(String oldToken);

    String logout();

    UserResponse editUserDetails(EditUserRequest editUserDto);

    UserResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    UserResponse resetPassword(PasswordResetRequest passwordRequest, String token);
//    public UserResponse forgotPassword1(ForgotPasswordRequest forgotPasswordRequest);
UserResponse resetPasswordWithoutToken(ResetPasswordWithoutToken passwordRequest);

    UserResponse updatePassword(ChangePasswordRequest changePasswordRequest);

    List<UserResponse> getAllUsers();
    UserResponse getUser();
    ResponseEntity<Void> deleteUser(Long userId);
}
