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
        return genericResponseBuilder("Validation Error: " + exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleGenericException(Exception exception) {
        log.error("Unexpected Error: {}", exception.getMessage(), exception);
        return genericResponseBuilder("Unexpected Error",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleBusinessException(BusinessException exception) {
        return genericResponseBuilder("Business Exception: " + exception.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return genericResponseBuilder("Resource Not Found: " + exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<AuthApiGeneralResponse<?>> handlerDuplicateKey(DatabaseException exception) {
        return genericResponseBuilder("Database Error: " + exception.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<AuthApiGeneralResponse<?>> genericResponseBuilder(String reason, HttpStatus httpStatus) {
        log.error(reason);
        AuthApiGeneralResponse<?> errorResponse = new AuthApiGeneralResponse<>("Failure",
                reason, null);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
