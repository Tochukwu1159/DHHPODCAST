package com.netshiftdigital.dhhpodcast.service.impl;

import com.netshiftdigital.dhhpodcast.Security.CustomUserDetailService;
import com.netshiftdigital.dhhpodcast.Security.JwtUtil;
import com.netshiftdigital.dhhpodcast.exceptions.AuthenticationFailedException;
import com.netshiftdigital.dhhpodcast.exceptions.BadRequestException;
import com.netshiftdigital.dhhpodcast.exceptions.ResourceNotFoundException;
import com.netshiftdigital.dhhpodcast.exceptions.UserPasswordMismatchException;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.payloads.requests.LoginRequest;
import com.netshiftdigital.dhhpodcast.payloads.responses.LoginResponse;
import com.netshiftdigital.dhhpodcast.payloads.responses.UserObj;
import com.netshiftdigital.dhhpodcast.payloads.responses.UserResponse;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.UserService;
import com.netshiftdigital.dhhpodcast.utils.Constants;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import com.netshiftdigital.dhhpodcast.payloads.requests.UserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService


{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetailService customUserDetailsService;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse createAdmin(UserRequestDto userRequest) {
        Optional<User> student = userRepository.findByEmail(userRequest.getEmail());

        if (student.isPresent()) {

            throw new ResourceNotFoundException("Email already exist");

        }
        if (!Constants.validatePassword(userRequest.getPassword(), userRequest.getConfirmPassword())){
            throw new UserPasswordMismatchException("Password does not match");

        }

        if(!isValidEmail(userRequest.getEmail())){
            throw new BadRequestException("Error: Email must be valid");
        }

        if(userRequest.getPassword().length() < 8 || userRequest.getConfirmPassword().length() < 8 ){
            throw new BadRequestException("Password is too short, should be minimum of 8 character long");
        }


        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLatName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .isVerified(true)
                .phoneNumber(userRequest.getPhoneNumber())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(Roles.ADMIN)
                .build();
        User savedUser = userRepository.save(newUser);


        return UserResponse.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public UserResponse createBuyer(UserRequestDto userRequest) {
        Optional<User> student = userRepository.findByEmail(userRequest.getEmail());

        if (student.isPresent()) {

            throw new ResourceNotFoundException("Email already exist");

        }
        if (!Constants.validatePassword(userRequest.getPassword(), userRequest.getConfirmPassword())){
            throw new UserPasswordMismatchException("Password does not match");

        }

        if(!isValidEmail(userRequest.getEmail())){
            throw new BadRequestException("Error: Email must be valid");
        }

        if(userRequest.getPassword().length() < 8 || userRequest.getConfirmPassword().length() < 8 ){
            throw new BadRequestException("Password is too short, should be minimum of 8 character long");
        }


        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLatName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .isVerified(true)
                .phoneNumber(userRequest.getPhoneNumber())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(Roles.SUBSCRIBERS)
                .build();
        User savedUser = userRepository.save(newUser);


        return UserResponse.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .build();
    }

    public LoginResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            if (!authenticate.isAuthenticated()) {
                throw new UserPasswordMismatchException("Wrong email or password");
            }

//            UserDetails userDetails = loadUserByUsername(loginRequest.getEmail());
            Optional<User> userDetails = userRepository.findByEmail(loginRequest.getEmail());

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = "Bearer " + jwtUtil.generateToken(loginRequest.getEmail());

            // Create a UserDto object containing user details
            UserObj userDto = new UserObj();
            userDto.setFirstName(userDetails.get().getFirstName());
            userDto.setLastName(userDetails.get().getLastName());
            userDto.setEmail(userDetails.get().getEmail());

            return new LoginResponse(token, userDto);
        } catch (BadCredentialsException e) {
            // Handle the "Bad credentials" error here
            throw new AuthenticationFailedException("Wrong email or password");
        }
    }


    private boolean isValidEmail(String email) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (email == null) {
            throw new BadRequestException("Error: Email cannot be null");
        }
        Matcher m = p.matcher(email);
        return m.matches();
    }
    private boolean existsByMail(String email) {
        return userRepository.existsByEmail(email);


    }

}
