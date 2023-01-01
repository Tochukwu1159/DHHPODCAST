package com.netshiftdigital.dhhpodcast.controllers;


import com.netshiftdigital.dhhpodcast.models.ConfirmationToken;
import com.netshiftdigital.dhhpodcast.payloads.requests.LoginRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.ResetPasswordWithoutToken;
import com.netshiftdigital.dhhpodcast.payloads.requests.UserRequestDto;
import com.netshiftdigital.dhhpodcast.payloads.responses.*;
import com.netshiftdigital.dhhpodcast.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/admin")
    public UserResponse createAdmin(@RequestBody @Valid UserRequestDto userRequest)  {
        return userService.createAdmin(userRequest);
    }
    @PostMapping("/subscriber")
    public UserResponse createUser(@RequestBody @Valid UserRequestDto userRequest)  {
        return userService.createUser(userRequest);
    }

//    @WebServlet("/example")
//    public class ExampleServlet extends HttpServlet {
//        @Override
//        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, InaccessibleObjectException, IOException {
//            String paramValue = request.getParameter("paramName");
//            String headerValue = request.getHeader("HeaderName");
//
//            request.setAttribute("attributeName", "attributeValue");
//
//            // Forward the request to another resource
//            RequestDispatcher dispatcher = request.getRequestDispatcher("/anotherResource");
//            dispatcher.forward(request, response);
//        }
//    }

    @PostMapping("/loginUser")
    public LoginResponse loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);

    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam("token") String token){
        return new ResponseEntity<>(userService.verifyUser(token), HttpStatus.OK);


    }

    @GetMapping("/resend-verify")
    public ResponseEntity<ConfirmationToken> generateNewToken(@RequestParam("token") String token){

        return new ResponseEntity<>(userService.generateNewToken(token),HttpStatus.OK);

    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout() {
        userService.logout();
        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    };

    @PutMapping("/edit")
    public ResponseEntity<UserResponse> editUserDetails( @RequestBody @Valid EditUserRequest editUserDto){
        return new ResponseEntity<>(userService.editUserDetails(editUserDto),HttpStatus.OK);
    }
    @PostMapping("/forgot-password")
    public  ResponseEntity<UserResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest){
        return new ResponseEntity<>(userService.forgotPassword(forgotPasswordRequest),HttpStatus.OK);
    }

    @GetMapping("/reset-password")
    public  ResponseEntity<UserResponse> resetPassword(@RequestBody @Valid PasswordResetRequest passwordResetRequest, @RequestParam("token") String token) {
        return new ResponseEntity<>(userService.resetPassword(passwordResetRequest, token), HttpStatus.OK);
    }

    @PostMapping("/reset-passwords")
    public  ResponseEntity<UserResponse> resetPasswordWithoutToken(@RequestBody @Valid ResetPasswordWithoutToken passwordResetRequest) {
        return new ResponseEntity<>(userService.resetPasswordWithoutToken(passwordResetRequest), HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<UserResponse> updatePassword (@RequestBody @Valid ChangePasswordRequest changePasswordRequest){
        return new ResponseEntity<>(userService.updatePassword(changePasswordRequest),HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId).hasBody();
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
