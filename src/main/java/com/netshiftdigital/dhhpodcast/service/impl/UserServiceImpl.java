package com.netshiftdigital.dhhpodcast.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netshiftdigital.dhhpodcast.Security.CustomUserDetailService;
import com.netshiftdigital.dhhpodcast.Security.JwtUtil;
import com.netshiftdigital.dhhpodcast.Security.SecurityConfig;
import com.netshiftdigital.dhhpodcast.exceptions.*;
import com.netshiftdigital.dhhpodcast.models.ConfirmationToken;
import com.netshiftdigital.dhhpodcast.models.PasswordResetToken;
import com.netshiftdigital.dhhpodcast.models.Profile;
import com.netshiftdigital.dhhpodcast.models.User;
import com.netshiftdigital.dhhpodcast.payloads.requests.EmailDetails;
import com.netshiftdigital.dhhpodcast.payloads.requests.LoginRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.ResetPasswordWithoutToken;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.repositories.ConfirmationTokenRepository;
import com.netshiftdigital.dhhpodcast.repositories.PasswordResetTokenRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserProfileRepository;
import com.netshiftdigital.dhhpodcast.repositories.UserRepository;
import com.netshiftdigital.dhhpodcast.service.BlacklistService;
import com.netshiftdigital.dhhpodcast.service.ConfirmationTokenService;
import com.netshiftdigital.dhhpodcast.service.EmailService;
import com.netshiftdigital.dhhpodcast.service.UserService;
import com.netshiftdigital.dhhpodcast.utils.Constants;
import com.netshiftdigital.dhhpodcast.utils.Roles;
import com.netshiftdigital.dhhpodcast.payloads.requests.UserRequestDto;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService


{

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;

    private final  HttpServletRequest httpServletRequest;

    private final  ObjectMapper objectMapper;


    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailService customUserDetailsService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final BlacklistService blacklistService;

    private final  ConfirmationTokenRepository confirmationTokenRepository;
    private final UserProfileRepository userProfileRepository;


    @Override
    public UserResponse createAdmin(UserRequestDto userRequest) throws MessagingException {
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
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .isVerified(true)
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(Roles.ADMIN)
                .build();
        User savedUser = userRepository.save(newUser);

        Profile userProfile = new Profile();
        userProfile.setUser(savedUser);
        userProfile.setPhoneNumber(userProfile.getPhoneNumber());
        userProfileRepository.save(userProfile);
        String token = jwtUtil.generateToken(userRequest.getEmail());
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now().plusMinutes(80),
                newUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .templateName("email-template-admin")
                .model(Map.of("name", savedUser.getFirstName() + " " + savedUser.getLastName()))
                .build();
        emailService.sendHtmlEmail(emailDetails);


        return UserResponse.builder()
                .id(savedUser.getId())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .build();
    }

    @Override
    public UserResponse createUser(UserRequestDto userRequest) throws MessagingException {
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
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .isVerified(false)
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .roles(Roles.SUBSCRIBERS)
                .build();
        User savedUser = userRepository.save(newUser);

        Profile userProfile = Profile.builder()
                .user(savedUser)
                .phoneNumber(userRequest.getPhoneNumber())
                .build();
        userProfileRepository.save(userProfile);
        String token = jwtUtil.generateToken(userRequest.getEmail());
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now().plusMinutes(30),
                newUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        Map<String, Object> model = new HashMap<>();
        model.put("token", "http://localhost:8080/api/users/verify?token=" + token);
        model.put("name", savedUser.getFirstName() + " " + savedUser.getLastName());


        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .templateName("email-template-user")
                .model(model)
                .build();
        emailService.sendHtmlEmail(emailDetails);


        return UserResponse.builder()
                .id(savedUser.getId())
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

            if(!userDetails.get().getIsVerified()){
                throw new UserNotVerifiedException("User is not verified");
            }

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = "Bearer " + jwtUtil.generateToken(loginRequest.getEmail());

            // Create a UserDto object containing user details
            UserObj userDto = new UserObj();
            userDto.setFirstName(userDetails.get().getFirstName());
            userDto.setLastName(userDetails.get().getLastName());
            userDto.setEmail(userDetails.get().getEmail());
            userDto.setRole(userDetails.get().getRoles());


            return new LoginResponse(token, userDto);
        } catch (BadCredentialsException e) {
            // Handle the "Bad credentials" error here
            throw new AuthenticationFailedException("Wrong email or password");
        }
    }


    @Override
    public String verifyUser(String userToken) {
        try {
            ConfirmationToken confirmationToken = confirmationTokenService.getToken(userToken);
            if (confirmationToken == null) {
                throw new ResourceNotFoundException("Token not found");
            }
            if (confirmationToken.getConfirmedAt() != null) {
                throw new EntityExistsException("Email already confirmed");
            }
            LocalDateTime expiredAt = confirmationToken.getExpiresAt();
            if (expiredAt.isBefore(LocalDateTime.now())) {
                throw new ResourceNotFoundException("Token expired");
            }
            confirmationTokenService.setConfirmedAt(userToken);
            User user = confirmationToken.getUser();
            Optional<User> userOptional = userRepository.findById(user.getId());
            if (userOptional.isEmpty()) {
                throw new ResourceNotFoundException("User does not exist");
            }
            User verifiedUser = userOptional.get();
            verifiedUser.setIsVerified(true);
            userRepository.save(verifiedUser);
            return "confirmed";
        } catch (Exception ex) {
            throw new RuntimeException("Failed to verify user "+ex);
        }
    }


    @Override
    public ConfirmationToken generateNewToken(String oldToken) {
        try {
            ConfirmationToken verificationToken = confirmationTokenRepository.findByToken(oldToken);
            if (verificationToken == null) {
                throw new ResourceNotFoundException("Token not found");
            }
            verificationToken.setToken(UUID.randomUUID().toString());
            LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(40);
            verificationToken.setExpiresAt(expirationDate);
            confirmationTokenRepository.save(verificationToken);
            return verificationToken;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate new token "+ex);
        }
    }

    @Override
    public String logout() {
        try {
            String token = httpServletRequest.getHeader("Authorization");
            if (token == null || !token.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Invalid token format");
            }

            // Extract token from the Authorization header
            token = token.substring(7); // Remove "Bearer " prefix

            blacklistService.blacklistToken(token);

            return "Logout Successful";
        } catch (Exception ex) {
            throw new RuntimeException("Error during logout "  +ex); // Or any other appropriate error handling
        }
    }



    @Override
    public UserResponse editUserDetails(EditUserRequest editUserDto) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail(); // Assuming this method retrieves the authenticated user's email
            System.out.println(email + "email");
            Object loggedInUsername1 = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(loggedInUsername1 + "loggedInUsername1");

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Profile profile = userProfileRepository.findByUser(user);

            if (user == null) {
                throw new ResourceNotFoundException("User does not exist");
            }

            user.setFirstName(editUserDto.getFirstName());
            user.setLastName(editUserDto.getLastName());
            User updatedUser = userRepository.save(user);

            profile.setPhoneNumber(editUserDto.getPhoneNumber());
            userProfileRepository.save(profile);

            return mapToUserResponse(updatedUser);
        } catch (Exception ex) {
            throw  new RuntimeException("Error editing password "+ex);
        }
    }


    @Override
    public UserResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(forgotPasswordRequest.getEmail());

            if (!userOptional.isPresent()) {
                throw new ResourceNotFoundException("User with provided Email not found");
            }

            User user = userOptional.get();
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(forgotPasswordRequest.getEmail());

            String token = new JwtUtil().generateToken(userDetails.getUsername());

            PasswordResetToken existingToken = passwordResetTokenRepository.findByUsersDetails(user);

            if (existingToken != null) {
                existingToken.setToken(token);
            } else {
                PasswordResetToken passwordResetTokenEntity = new PasswordResetToken();
                passwordResetTokenEntity.setToken(token);
                passwordResetTokenEntity.setUsersDetails(user);
                passwordResetTokenRepository.save(passwordResetTokenEntity);
            }

            Map<String, Object> model = new HashMap<>();
            model.put("token", "http://localhost:8080/api/users/resetPassword?token=" + token);

            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(forgotPasswordRequest.getEmail())
                    .subject("PASSWORD RESET OTP")
                    .templateName("password-reset-email")
                    .model(model)
                    .build();

            try {
                emailService.sendHtmlEmail(emailDetails);
            } catch (Exception e) {
                throw new EmailSendingException("Failed to send the password reset email "+e);
            }

            return mapToUserResponse(user);
        } catch (Exception ex) {
            // Handle any exceptions here
            ex.printStackTrace(); // Or log the exception
            throw new RuntimeException("Error ");        }
    }


    @Override
    @Transactional
    public UserResponse resetPassword(PasswordResetRequest passwordRequest, String token) {
        try {
            if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())) {
                throw new ResourceNotFoundException("Password do not match");
            }

            String email = jwtUtil.extractUsername(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            if (passwordRequest.getNewPassword().length() < 8 || passwordRequest.getConfirmPassword().length() < 8) {
                throw new BadRequestException("Error: Password is too short");
            }

            if (!isValidPassword(passwordRequest.getNewPassword())) {
                throw new BadRequestException("Error: Password must contain at least 8 characters, Uppercase, Lowercase, and Number");
            }

            if (jwtUtil.isTokenExpired(token)) {
                throw new TokenExpiredException("Token has expired");
            }

            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);

            passwordResetTokenRepository.deleteByToken(token);

            return mapToUserResponse(user);
        } catch (Exception ex) {
            throw new RuntimeException("Errow re-seting Pasword "+ex);
        }
    }

    @Override
    @Transactional
    public UserResponse resetPasswordWithoutToken(ResetPasswordWithoutToken passwordRequest) {
        try {
            if (!passwordRequest.getNewPassword().equals(passwordRequest.getConfirmPassword())) {
                throw new ResourceNotFoundException("Password do not match");
            }

            User user = userRepository.findByEmail(passwordRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            if (passwordRequest.getNewPassword().length() < 8 || passwordRequest.getConfirmPassword().length() < 8) {
                throw new BadRequestException("Error: Password is too short");
            }

            if (!isValidPassword(passwordRequest.getNewPassword())) {
                throw new BadRequestException("Error: Password must contain at least 8 characters, Uppercase, Lowercase, and Number");
            }

            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);


            return mapToUserResponse(user);
        } catch (Exception ex) {
            throw new RuntimeException("Errow re-seting Pasword "+ex);
        }
    }



    @Override
    public UserResponse updatePassword(ChangePasswordRequest changePasswordRequest) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail(); // Assuming this method retrieves the authenticated user's email
            String oldPassword = changePasswordRequest.getOldPassword();
            String newPassword = changePasswordRequest.getNewPassword();
            String confirmPassword = changePasswordRequest.getConfirmPassword();

            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String encodedPassword = user.getPassword();
                boolean isPasswordAMatch = passwordEncoder.matches(oldPassword, encodedPassword);

                if (!isPasswordAMatch) {
                    throw new BadRequestException("Old Password does not match");
                }

                if (newPassword.length() < 8 || confirmPassword.length() < 8) {
                    throw new BadRequestException("Error: Password is too short");
                }

                if (!isValidPassword(newPassword)) {
                    throw new BadRequestException("Error: password must contain at least 8 characters, Uppercase, LowerCase and Number");
                }

                boolean isPasswordEquals = newPassword.equals(confirmPassword);

                if (!isPasswordEquals) {
                    throw new BadRequestException("New Password does not match confirm password");
                }

                user.setPassword(passwordEncoder.encode(newPassword));

                userRepository.save(user);

                return mapToUserResponse(user);
            } else {
                throw new ResourceNotFoundException("User not found");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error updating password "+ex);
        }
    }

    @Override
    public List<UserResponse> getAllUsers() {
        try {
            List<User> userList = userRepository.findAll();
            return userList.stream()
                    .map(this::mapToUserResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RuntimeException("Error gettinng the user list "+ex);
        }
    }


    @Override
    public UserResponse getUser() {
        try {
            String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            if (loggedInEmail.equals("anonymousUser")) {
                throw  new ResourceNotFoundException("not found");
            }
            User user = userRepository.findByEmail(loggedInEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return mapToUserResponse(user);
        } catch (Exception ex) {
            throw new RuntimeException("Error occurred while fetching user data "+ex);
        }
    }


    public ResponseEntity<Void> deleteUser(Long userId) {
        try {
            String email = SecurityConfig.getAuthenticatedUserEmail();
            User admin = userRepository.findByEmailAndRoles(email, Roles.ADMIN);

            if (!admin.getIsVerified()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return ResponseEntity.noContent().build(); // User deleted successfully
            } else {
                throw  new ResourceNotFoundException("User not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user " +e);
        }
    }




    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            throw new BadRequestException("Error: Password cannot be null");
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }


    public UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
     userResponse.setFirstName(user.getFirstName());
     userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
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
