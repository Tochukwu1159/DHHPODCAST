package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.LoginRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.LoginResponse;
import com.netshiftdigital.dhhpodcast.payloads.requests.UserRequestDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.UserResponse;

public interface UserService
{
    UserResponse createAdmin(UserRequestDto userRequest);
    UserResponse createBuyer(UserRequestDto userRequest);

    LoginResponse loginUser(LoginRequest loginRequest);
}
