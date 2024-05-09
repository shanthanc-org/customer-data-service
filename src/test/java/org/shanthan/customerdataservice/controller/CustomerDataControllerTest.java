package org.shanthan.customerdataservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.shanthan.customerdataservice.model.*;
import org.shanthan.customerdataservice.repository.CustomerEntity;
import org.shanthan.customerdataservice.service.CustomerService;
import org.shanthan.customerdataservice.util.CustomerUtil;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.*;
import static org.shanthan.customerdataservice.util.CustomerTestConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

class CustomerDataControllerTest {

    @InjectMocks
    private CustomerDataController subject;

    @Mock
    private CustomerService customerService;

    private Address newAddress;
    private Customer customer1;

    private Customer customer2;

    private Customer customer3;

    private Customer updatedCustomer1;

    private List<Customer> customerFirstNameList;

    private List<Customer> customerLastNameList;

    private List<CustomerEntity> entityList;

    private String accNum1, accNum2, accNum3;


    @BeforeEach
    void setUp() {
        openMocks(this);

        newAddress = Address.builder()
                .street("555 W New Street")
                .city("Chicago")
                .state("IL")
                .zipCode("65656")
                .build();

        accNum1 = CustomerUtil.generateAccountNumber();
        customer1 = Customer.builder()
                .accountKey(TEST_ACC_KEY)
                .accountNumber(accNum1)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL)
                .phoneNumber(TEST_PHONE_NUMBER)
                .dateOfBirth(TEST_DOB)
                .ssn(TEST_SSN)
                .address(Address.builder()
                        .street(TEST_STREET)
                        .city(TEST_CITY)
                        .state(TEST_STATE)
                        .zipCode(TEST_ZIPCODE)
                        .build())
                .build();

        updatedCustomer1 = Customer.builder()
                .accountKey(TEST_ACC_KEY)
                .accountNumber(accNum1)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL)
                .phoneNumber(TEST_PHONE_NUMBER)
                .dateOfBirth(TEST_DOB)
                .ssn(TEST_SSN)
                .address(newAddress)
                .build();

        accNum2 = CustomerUtil.generateAccountNumber();
        customer2 = Customer.builder()
                .accountKey(TEST_ACC_KEY_2)
                .accountNumber(accNum2)
                .firstName(TEST_FIRST_NAME_2)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL_2)
                .phoneNumber(TEST_PHONE_NUMBER_2)
                .dateOfBirth(TEST_DOB_2)
                .ssn(TEST_SSN_2)
                .address(Address.builder()
                        .street(TEST_STREET_2)
                        .city(TEST_CITY_2)
                        .state(TEST_STATE_2)
                        .zipCode(TEST_ZIPCODE_2)
                        .build())
                .build();

        accNum3 = CustomerUtil.generateAccountNumber();
        customer3 = Customer.builder()
                .accountKey(TEST_ACC_KEY_3)
                .accountNumber(accNum3)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME_3)
                .email(TEST_EMAIL_3)
                .phoneNumber(TEST_PHONE_NUMBER_3)
                .dateOfBirth(TEST_DOB_3)
                .ssn(TEST_SSN_3)
                .address(Address.builder()
                        .street(TEST_STREET_3)
                        .city(TEST_CITY_3)
                        .state(TEST_STATE_3)
                        .zipCode(TEST_ZIPCODE_3)
                        .build())
                .build();

        customerFirstNameList = new ArrayList<>();
        customerLastNameList = new ArrayList<>();

        customerFirstNameList.add(customer1);
        customerFirstNameList.add(customer3);

        customerLastNameList.add(customer1);
        customerLastNameList.add(customer2);

    }

    @Test
    void givenValidCustomerRequest_whenAddCustomer_thenReturnCustomerWith201HttpStatus() {
        CustomerAddSuccessResponse expectedBody = CustomerAddSuccessResponse.builder()
                .accountKey(TEST_ACC_KEY)
                .accountNumber(accNum1)
                .build();

        when(customerService.saveCustomer(any(Customer.class))).thenReturn(expectedBody);
        ResponseEntity<CustomerAddSuccessResponse> responseEntity = subject.addCustomer(customer1);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(CREATED, responseEntity.getStatusCode());
        assertEquals(TEST_ACC_KEY, responseEntity.getBody().getAccountKey());
        assertEquals(accNum1, responseEntity.getBody().getAccountNumber());
    }

    @Test
    void givenValidAddressRequest_whenUpdateCustomer_thenReturnResponseWith200HttpStatus() {
        when(customerService.updateCustomerAddress(anyString(), any(Address.class))).thenReturn(updatedCustomer1);

        UpdateAddress updateAddress = UpdateAddress.builder()
                .accountKey(updatedCustomer1.getAccountKey())
                .address(updatedCustomer1.getAddress())
                .build();

        ResponseEntity<DefaultSuccessResponse> responseEntity = subject.updateCustomerAddress(updateAddress);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(TEST_ACC_KEY, responseEntity.getBody().getAccountKey());
    }

    @Test
    void givenValidAccountKey_whenDeleteCustomer_thenReturnResponseWith200HttpStatus() {
        doNothing().when(customerService).deleteCustomer(anyString());

        ResponseEntity<DefaultSuccessResponse> responseEntity = subject.deleteCustomer(TEST_ACC_KEY);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(TEST_ACC_KEY, responseEntity.getBody().getAccountKey());
    }

    @Test
    void givenValidAccountKey_whenGetCustomer_thenReturnResponseWith200HttpStatus() {
        when(customerService.getCustomerDetails(anyString())).thenReturn(customer1);

        ResponseEntity<Customer> responseEntity = subject.getCustomer(TEST_ACC_KEY);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(TEST_FIRST_NAME, responseEntity.getBody().getFirstName());
    }

    @Test
    void givenValidFirstName_whenGetCustomer_thenReturnResponseWith200HttpStatus() {
        when(customerService.getCustomersByFirstName(anyString())).thenReturn(customerFirstNameList);

        ResponseEntity<List<Customer>> responseEntity = subject.getCustomerByFirstName(TEST_FIRST_NAME);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }

    @Test
    void givenValidLastName_whenGetCustomer_thenReturnResponseWith200HttpStatus() {
        when(customerService.getCustomersByLastName(anyString())).thenReturn(customerLastNameList);

        ResponseEntity<List<Customer>> responseEntity = subject.getCustomerByLastName(TEST_LAST_NAME);

        assertNotNull(responseEntity);
        assertNotNull(responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
    }




}