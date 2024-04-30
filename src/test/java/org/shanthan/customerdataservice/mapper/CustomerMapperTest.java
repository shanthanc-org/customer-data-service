package org.shanthan.customerdataservice.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.shanthan.customerdataservice.model.Address;
import org.shanthan.customerdataservice.model.Customer;
import org.shanthan.customerdataservice.repository.CustomerEntity;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.shanthan.customerdataservice.util.CustomerTestConstants.*;


class CustomerMapperTest {

    @InjectMocks
    private CustomerMapper customerMapper;

    private Customer customer;

    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        Address address = Address.builder()
                .street(TEST_STREET)
                .city(TEST_CITY)
                .state(TEST_STATE)
                .zipCode(TEST_ZIPCODE)
                .build();

        customer = Customer.builder()
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL)
                .ssn(TEST_SSN)
                .dateOfBirth(TEST_DOB)
                .address(address)
                .build();

        customerEntity = CustomerEntity.builder()
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL)
                .ssn(TEST_SSN)
                .dateOfBirth(TEST_DOB)
                .address(ADDRESS_STRING)
                .build();
    }

    @Test
    void givenCustomerObject_whenMapToCustomerEntity_thenReturnCustomerEntity() {
        CustomerEntity customerEntity = customerMapper.mapToEntity(customer);
        assertNotNull(customerEntity);
        assertEquals(TEST_FIRST_NAME, customerEntity.getFirstName());
        assertEquals(TEST_LAST_NAME, customerEntity.getLastName());
        assertEquals(TEST_EMAIL, customerEntity.getEmail());
        assertEquals(TEST_SSN, customerEntity.getSsn());
        assertEquals(TEST_DOB, customerEntity.getDateOfBirth());
        assertEquals(ADDRESS_STRING, customerEntity.getAddress());
    }

    @Test
    void givenCustomerEntity_whenMapToCustomerDomain_thenReturnCustomer() {
        Customer customer = customerMapper.mapToDomain(customerEntity);
        assertNotNull(customer);
        assertEquals(TEST_FIRST_NAME, customer.getFirstName());
        assertEquals(TEST_LAST_NAME, customer.getLastName());
        assertEquals(TEST_EMAIL, customer.getEmail());
        assertEquals(TEST_SSN, customer.getSsn());
        assertEquals(TEST_DOB, customer.getDateOfBirth());
        assertEquals(TEST_STREET, customer.getAddress().getStreet());
        assertEquals(TEST_CITY, customer.getAddress().getCity());
        assertEquals(TEST_STATE, customer.getAddress().getState());
        assertEquals(TEST_ZIPCODE, customer.getAddress().getZipCode());
    }

}