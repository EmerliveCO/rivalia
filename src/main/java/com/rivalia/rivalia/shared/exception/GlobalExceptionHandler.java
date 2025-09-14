package com.rivalia.rivalia.shared.exception;

import com.rivalia.rivalia.infraestructure.outbund.webclient.dto.AuthApiGeneralResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException exception) {
        log.error("Api Exception: {} - {}", exception.getStatus(), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(exception.getResponse());
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleWebClientException(WebClientResponseException exception) {
        log.error("Web Client Exception: {} - {}", exception.getStatusCode(), exception.getMessage());
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                "External Service Error: " + exception.getStatusCode() + " - " + exception.getMessage(),
                null);
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleWebClientRequestException(WebClientRequestException exception) {
        log.error("Web Request Error: {}", exception.getMessage());
        if (exception.getMessage().contains("Connection refused")) {
            AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                    "Web Request Exception: Connection refused", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                "Web Request Exception: " + exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Method Argument Not Valid Exception: {}", exception.getMessage());
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                "Validation Error: " + exception.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleGenericException(Exception exception) {
        log.error("Unexpected Error: {}", exception.getMessage(), exception);
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                "Internal Server Error", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleBusinessException(BusinessException exception) {
        log.error("Business Exception: {}", exception.getMessage());
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        log.error("Resource Not Found: {}", exception.getMessage());
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handlerDuplicateKey(DatabaseException exception) {
        log.error("Database Error: {}", exception.getLocalizedMessage());
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                "Database error: " + exception.getLocalizedMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
