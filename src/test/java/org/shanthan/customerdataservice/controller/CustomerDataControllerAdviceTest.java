package org.shanthan.customerdataservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.shanthan.customerdataservice.exception.CustomerDataException;
import org.shanthan.customerdataservice.model.ErrorResponse;
import org.springframework.data.rest.webmvc.support.HttpMethodHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.shanthan.customerdataservice.util.CustomerTestConstants.*;
import static org.springframework.http.HttpStatus.*;

class CustomerDataControllerAdviceTest {

    @InjectMocks
    private CustomerDataControllerAdvice subject;

    @Mock
    private HttpMessageNotReadableException hme;

    @Mock
    private HttpRequestMethodNotSupportedException hre;

    @Mock
    private MethodArgumentNotValidException mve;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleGeneralException() {
        Exception ex = new RuntimeException(SOME_RUNTIME_EXCEPTION);
        ResponseEntity<ErrorResponse> responseEntity = subject.handleGeneralException(ex);

        assertEquals(INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getErrorMessage());
    }

    @Test
    void handleAppException() {
        CustomerDataException cde = new CustomerDataException(BAD_GATEWAY, SOME_EXCEPTION);
        ResponseEntity<ErrorResponse> responseEntity = subject.handleAppException(cde);

        assertEquals(BAD_GATEWAY, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getErrorMessage());
    }

    @Test
    void handleHttpMessageNotReadableException() {
        Throwable cause = new RuntimeException(SOME_SPECIFIC_CAUSE);
        when(hme.getMostSpecificCause()).thenReturn(cause);
        when(hme.getMessage()).thenReturn(SOME_MESSAGE);

        ResponseEntity<ErrorResponse> responseEntity = subject.handleHttpMessageNotReadableException(hme);

        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(SOME_MESSAGE, responseEntity.getBody().getErrorMessage());
        assertEquals(SOME_SPECIFIC_CAUSE, responseEntity.getBody().getErrorFields().get(SPECIFIC_CAUSE));
    }

    @Test
    void handleHttpRequestMethodNotSupportedException() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400),
                SOME_PROBLEM_DETAIL);
        when(hre.getStatusCode()).thenReturn(BAD_REQUEST);
        when(hre.getMethod()).thenReturn(SOME_METHOD);
        when(hre.getBody()).thenReturn(problemDetail);
        when(hre.getMessage()).thenReturn(SOME_MESSAGE);

        ResponseEntity<ErrorResponse> responseEntity = subject.handleHttpRequestMethodNotSupportedException(hre);

        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(SOME_MESSAGE, responseEntity.getBody().getErrorMessage());
        assertEquals(SOME_METHOD, responseEntity.getBody().getErrorFields().get(METHOD));
        assertEquals(SOME_PROBLEM_DETAIL, responseEntity.getBody().getErrorFields().get(PROBLEM_DETAIL));
    }

    @Test
    void handleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        List<FieldError> fieldErrorList = new ArrayList<>();
        FieldError fieldError = new FieldError(SOME_OBJECT_NAME, SOME_FIELD_MESSAGE, SOME_DEFAULT_MESSAGE);
        fieldErrorList.add(fieldError);
        FieldError[] fieldErrors = fieldErrorList.toArray(new FieldError[0]);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldErrors));
        when(mve.getBindingResult()).thenReturn(bindingResult);
        when(mve.getMessage()).thenReturn(SOME_MESSAGE);


        ResponseEntity<ErrorResponse> responseEntity = subject.handleMethodArgumentNotValidException(mve);

        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(SOME_MESSAGE, responseEntity.getBody().getErrorMessage());
        assertEquals(SOME_DEFAULT_MESSAGE, responseEntity.getBody().getErrorFields().get(SOME_FIELD_MESSAGE));
    }
}