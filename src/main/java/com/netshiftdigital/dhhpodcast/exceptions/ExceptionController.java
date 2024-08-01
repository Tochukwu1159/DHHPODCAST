package com.netshiftdigital.dhhpodcast.exceptions;
import com.netshiftdigital.dhhpodcast.payloads.requests.Result;
import com.netshiftdigital.dhhpodcast.payloads.requests.StatusCode;
import com.netshiftdigital.dhhpodcast.payloads.responses.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

//import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(ApiResponse.error(errors),HttpStatus.NOT_FOUND);
    }


    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("Errors", errors);
        return errorResponse;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> ResourceNotFoundException(ResourceNotFoundException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> TokenExpiredException(TokenExpiredException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> CustomExpiredException(Throwable ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> UserAlreadyExistException(UserAlreadyExistException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.CONFLICT);
    }
    @ExceptionHandler(UserLoggedOutException.class)
    public ResponseEntity<?> UserLoggedOutException(UserLoggedOutException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<?> UsernameNotFoundException(UsernameNotFoundException ex) {
////        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<?> UserNotVerifiedException(UserNotVerifiedException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<?> EmailSendingException(EmailSendingException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(CustomInternalServerException.class)
    public ResponseEntity<?> CustomInternalServerException(CustomInternalServerException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> ServiceException(ServiceException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserPasswordMismatchException.class)
    public ResponseEntity<?> UserPasswordMismatchException(UserPasswordMismatchException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> AuthenticationFailedException(AuthenticationFailedException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(BadRequestException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<?> FileProcessingException(FileProcessingException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PodcastCategoryNotFoundException.class)
    public ResponseEntity<?> PodcastCategoryNotFoundException(PodcastCategoryNotFoundException ex) {
//        return new ResponseEntity<>(new ApiResponse<>((ex.getMessage()),false), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAuthenticationException(Exception ex) {
        return new Result(false, StatusCode.UNAUTHORIZED, "username or password is incorrect.", ex.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return new Result(false, StatusCode.UNAUTHORIZED, "Login credentials are missing.", ex.getMessage());
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result handleAccountStatusException(AccountStatusException ex) {
        return new Result(false, StatusCode.UNAUTHORIZED, "User account is abnormal.", ex.getMessage());
    }


//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    public String handleMissingParams(MissingServletRequestParameterException ex) {
//        String paramName = ex.getParameterName();
//        return "Missing parameter: " + paramName;
//    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Result handleAccessDeniedException(AccessDeniedException ex) {
        return new Result(false, StatusCode.FORBIDDEN, "No permission.", ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, "This API endpoint is not found.", ex.getMessage());
    }


//    @ExceptionHandler(value = {TokenExpiredException.class})
//    public ResponseEntity<Object> handleExpiredJwtException(TokenExpiredException ex) {
//        return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//    }
}


