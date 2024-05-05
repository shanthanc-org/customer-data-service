package org.shanthan.customerdataservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.shanthan.customerdataservice.exception.CustomerDataException;
import org.shanthan.customerdataservice.mapper.CustomerMapper;
import org.shanthan.customerdataservice.model.Address;
import org.shanthan.customerdataservice.model.Customer;
import org.shanthan.customerdataservice.model.CustomerAccDataResponse;
import org.shanthan.customerdataservice.repository.CustomerEntity;
import org.shanthan.customerdataservice.repository.CustomerRepository;
import org.shanthan.customerdataservice.util.CustomerUtil;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;
import static org.shanthan.customerdataservice.util.CustomerConstants.*;
import static org.shanthan.customerdataservice.util.CustomerTestConstants.*;
import static org.springframework.http.HttpStatus.*;

class CustomerServiceTest {

    @InjectMocks
    private CustomerService subject;

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper customerMapper;


    private Address newAddress;


    private Customer customer;

    private Customer customer2;

    private Customer customer3;

    private CustomerEntity customerEntity;

    private CustomerEntity customerEntity2;

    private CustomerEntity customerEntity3;

    private List<Customer> customerList;

    private List<CustomerEntity> entityList;

    @BeforeEach
    void setUp() {
        openMocks(this);
        newAddress = Address.builder()
                .street("555 W New Street")
                .city("Chicago")
                .state("IL")
                .zipCode("65656")
                .build();

        customerList = new ArrayList<>();
        entityList = new ArrayList<>();

        String accNum = CustomerUtil.generateAccountNumber();
        customer = Customer.builder()
                .accountKey(TEST_ACC_KEY)
                .accountNumber(accNum)
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


        customerEntity = CustomerEntity.builder()
                .accountKey(TEST_ACC_KEY)
                .accountNumber(accNum)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL)
                .phoneNumber(TEST_PHONE_NUMBER)
                .dateOfBirth(TEST_DOB)
                .ssn(TEST_SSN)
                .address(ADDRESS_STRING)
                .build();

        String accNum2 = CustomerUtil.generateAccountNumber();
        customer2 = Customer.builder()
                .accountKey(TEST_ACC_KEY_2)
                .accountNumber(accNum2)
                .firstName(TEST_FIRST_NAME_2)
                .lastName(TEST_LAST_NAME_2)
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


        customerEntity2 = CustomerEntity.builder()
                .accountKey(TEST_ACC_KEY_2)
                .accountNumber(accNum2)
                .firstName(TEST_FIRST_NAME_2)
                .lastName(TEST_LAST_NAME_2)
                .email(TEST_EMAIL_2)
                .phoneNumber(TEST_PHONE_NUMBER_2)
                .dateOfBirth(TEST_DOB_2)
                .ssn(TEST_SSN_2)
                .address(ADDRESS_STRING_2)
                .build();

        String accNum3 = CustomerUtil.generateAccountNumber();
        customer3 = Customer.builder()
                .accountKey(TEST_ACC_KEY_3)
                .accountNumber(accNum3)
                .firstName(TEST_FIRST_NAME_3)
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


        customerEntity3 = CustomerEntity.builder()
                .accountKey(TEST_ACC_KEY_3)
                .accountNumber(accNum3)
                .firstName(TEST_FIRST_NAME_3)
                .lastName(TEST_LAST_NAME_3)
                .email(TEST_EMAIL_3)
                .phoneNumber(TEST_PHONE_NUMBER_3)
                .dateOfBirth(TEST_DOB_3)
                .ssn(TEST_SSN_3)
                .address(ADDRESS_STRING_3)
                .build();

        customerList.add(customer);
        customerList.add(customer2);
        customerList.add(customer3);

        entityList.add(customerEntity);
        entityList.add(customerEntity2);
        entityList.add(customerEntity3);
    }

    @Test
    void givenValidCustomerObject_whenCalledForSaveCustomer_thenSaveCustomerInDb() {

        when(customerMapper.mapToEntity(any(Customer.class))).thenReturn(customerEntity);
        when(repository.saveAndFlush(any(CustomerEntity.class))).thenReturn(customerEntity);

        CustomerAccDataResponse result = subject.saveCustomer(customer);

        assertNotNull(result);
        assertNotNull(result.getAccountKey());
        assertNotNull(result.getAccountNumber());
        assertEquals(ACC_NUM_LEN, result.getAccountNumber().length());
    }

    @Test
    void givenNullCustomerObject_whenCalledForSaveCustomer_thenThrowException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () -> subject.saveCustomer(null));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());

    }

    @Test
    void givenEmptyCustomerObject_whenCalledForSaveCustomer_thenThrowException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.saveCustomer(Customer.builder().build()));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenValidCustomerAccKey_whenCalledForDeleteCustomer_thenDeleteCustomerFromDb() {
        when(repository.existsById(anyString())).thenReturn(true);
        subject.deleteCustomer(TEST_ACC_KEY);
        verify(repository).deleteById(eq(TEST_ACC_KEY));
    }

    @Test
    void givenNonExistentCustomerKey_whenCalledForDeleteCustomer_thenDeleteCustomerFromDbNotCalled() {
        when(repository.existsById(anyString())).thenReturn(false);
        subject.deleteCustomer(TEST_ACC_KEY);
        verify(repository, times(0)).deleteById(eq(TEST_ACC_KEY));
    }

    @Test
    void givenNullCustomerKey_whenCalledForDeleteCustomer_thenThrowAppException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () -> subject.deleteCustomer(null));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenEmptyCustomerKey_whenCalledForDeleteCustomer_thenThrowException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.deleteCustomer(""));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenCustomerAccKey_whenCalledToRetrieveCustomer_thenRetrieveCustomerFromDb() {
        customerEntity.setAccountKey(TEST_ACC_KEY);
        customer.setAccountKey(TEST_ACC_KEY);
        when(repository.existsById(anyString())).thenReturn(true);
        when(repository.getCustomerEntityByAccountKey(anyString())).thenReturn(customerEntity);
        when(customerMapper.mapToDomain(any(CustomerEntity.class))).thenReturn(customer);

        Customer result = subject.getCustomerDetails(TEST_ACC_KEY);

        assertNotNull(result);
        assertNotNull(result.getAccountKey());
        assertNotNull(result.getAccountNumber());
        assertEquals(TEST_FIRST_NAME, result.getFirstName());


    }

    @Test
    void givenNonExistentCustomerKey_whenCalledForRetrieveCustomer_thenReturnEmptyCustomer() {
        when(repository.existsById(anyString())).thenReturn(false);
        Customer result = subject.getCustomerDetails(TEST_ACC_KEY);

        assertNotNull(result);
        assertNull(result.getFirstName());
    }

    @Test
    void givenNullCustomerKey_whenCalledForRetrieveCustomer_thenThrowAppException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () -> subject.deleteCustomer(null));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenEmptyCustomerKey_whenCalledForRetrieveCustomer_thenThrowException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.deleteCustomer(""));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenCustomerAccKey_whenCalledForUpdateCustomer_thenRetrieveCustomerFromDb() {
        customer.setAddress(newAddress);
        Customer updatedCustomer = customer;

        customerEntity.setAddress(UPDATED_ADDRESS_STRING);
        CustomerEntity updatedEntity = customerEntity;

        when(repository.existsById(anyString())).thenReturn(true);
        when(repository.getCustomerEntityByAccountKey(anyString())).thenReturn(customerEntity);
        doNothing().when(repository).deleteById(anyString());
        when(customerMapper.mapToDomain(eq(customerEntity))).thenReturn(customer);
        when(customerMapper.mapToEntity(eq(updatedCustomer))).thenReturn(updatedEntity);
        when(customerMapper.mapToDomain(eq(updatedEntity))).thenReturn(updatedCustomer);
        subject.updateCustomerAddress(TEST_ACC_KEY, newAddress);
        verify(repository).saveAndFlush(eq(updatedEntity));
    }

    @Test
    void givenNullCustomerAccKey_whenCalledForUpdateCustomer_thenThrowNewAppException() {
        CustomerDataException ex = assertThrows(CustomerDataException.class, () ->
                subject.updateCustomerAddress(null, newAddress));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenEmptyCustomerAccKey_whenCalledForUpdateCustomer_thenThrowNewAppException() {
        CustomerDataException ex = assertThrows(CustomerDataException.class, () ->
                subject.updateCustomerAddress("", newAddress));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenNullAddress_whenCalledForUpdateCustomer_thenThrowNewAppException() {
        CustomerDataException ex = assertThrows(CustomerDataException.class, () ->
                subject.updateCustomerAddress(TEST_ACC_KEY, null));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenEmptyAddress_whenCalledForUpdateCustomer_thenThrowNewAppException() {
        CustomerDataException ex = assertThrows(CustomerDataException.class, () ->
                subject.updateCustomerAddress(TEST_ACC_KEY, null));
        assertEquals(INTERNAL_SERVER_ERROR, ex.getHttpStatus());
    }

    @Test
    void givenCustomerFirstName_whenCalledForRetrieveCustomerList_thenReturnCustomerList() {
        List<Customer> customersByFirstName = customerList.stream()
                .filter(c -> c.getFirstName().equals(TEST_FIRST_NAME))
                .toList();

        List<CustomerEntity> customerEntities = entityList.stream()
                .filter(c -> c.getFirstName().equals(TEST_FIRST_NAME))
                .toList();

        CustomerMapper customerMapper = new CustomerMapper();
        ReflectionTestUtils.setField(subject, "customerMapper", customerMapper);

        when(repository.findByFirstName(anyString())).thenReturn(customerEntities);

        List<Customer> result = subject.getCustomersByFirstName(TEST_FIRST_NAME);
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(TEST_FIRST_NAME, result.get(0).getFirstName());
        assertEquals(TEST_FIRST_NAME, result.get(1).getFirstName());

    }

    @Test
    void givenNullFirstName_whenCalledForCustomersByFirstName_thenThrowAppException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.getCustomersByFirstName(null));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenEmptyFirstName_whenCalledForCustomersByFirstName_thenThrowException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.getCustomersByFirstName(""));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenCustomerLastName_whenCalledForRetrieveCustomerList_thenReturnCustomerList() {
        List<CustomerEntity> customerEntities = entityList.stream()
                .filter(c -> c.getLastName().equals(TEST_LAST_NAME))
                .toList();

        CustomerMapper customerMapper = new CustomerMapper();
        ReflectionTestUtils.setField(subject, "customerMapper", customerMapper);

        when(repository.findByLastName(anyString())).thenReturn(customerEntities);

        List<Customer> result = subject.getCustomersByLastName(TEST_LAST_NAME);
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(TEST_LAST_NAME, result.get(0).getLastName());
        assertEquals(TEST_LAST_NAME, result.get(1).getLastName());

    }

    @Test
    void givenNullLastName_whenCalledForCustomersByLastName_thenThrowAppException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.getCustomersByLastName(null));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }

    @Test
    void givenEmptyLastName_whenCalledForCustomersByLastName_thenThrowException() {
        CustomerDataException exception = assertThrows(CustomerDataException.class, () ->
                subject.getCustomersByLastName(""));
        assertNotNull(exception);
        assertEquals(INTERNAL_SERVER_ERROR, exception.getHttpStatus());
    }
}