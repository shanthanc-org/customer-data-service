package org.shanthan.customerdataservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.shanthan.customerdataservice.exception.CustomerDataException;
import org.shanthan.customerdataservice.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.shanthan.customerdataservice.util.CustomerConstants.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class CustomerDataControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error(e.getMessage(), e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .errorMessage(e.getMessage())
                        .build());
    }

    @ExceptionHandler(CustomerDataException.class)
    public ResponseEntity<ErrorResponse> handleAppException(CustomerDataException cde) {
        log.error(cde.getMessage(), cde);

        return ResponseEntity.status(cde.getHttpStatus()).body(ErrorResponse.builder()
                .errorMessage(cde.getMessage())
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException hme) {
        log.error(hme.getMessage(), hme);
        Map<String, String> errorFields = new HashMap<>();
        errorFields.put(SPECIFIC_CAUSE, hme.getMostSpecificCause().getMessage());

        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .errorMessage(hme.getMessage())
                        .errorFields(errorFields)
                        .build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException
            (HttpRequestMethodNotSupportedException hre) {
        log.error(hre.getMessage(), hre);
        Map<String, String> errorFields = new HashMap<>();
        errorFields.put(METHOD, hre.getMethod());
        errorFields.put(PROBLEM_DETAIL, hre.getBody().getDetail());

        return ResponseEntity.status(hre.getStatusCode().value())
                .body(ErrorResponse.builder()
                        .errorMessage(hre.getMessage())
                        .errorFields(errorFields)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException mve) {
        log.error(mve.getMessage(), mve);
        Map<String, String> errorFields = new HashMap<>();
        mve.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorFields.put(fieldError.getField(), fieldError.getDefaultMessage());
            errorFields.put(CODE, fieldError.getCode());
        });

        return ResponseEntity.status(BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .errorMessage(mve.getMessage())
                        .errorFields(errorFields)
                        .build());

    }
}
