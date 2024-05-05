package org.shanthan.customerdataservice.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.shanthan.customerdataservice.exception.CustomerDataException;
import org.shanthan.customerdataservice.model.Address;
import org.shanthan.customerdataservice.model.Customer;
import org.shanthan.customerdataservice.repository.CustomerEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.util.ObjectUtils.*;

@Service
@Slf4j
public class CustomerMapper {

    public CustomerEntity mapToEntity(Customer customer) {
        CustomerEntity customerEntity;
        if(isEmpty(customer)) {
            throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR, "Customer object to be mapped is null ");
        }
        try {

            customerEntity = CustomerEntity.builder()
                    .accountKey(customer.getAccountKey() != null ? customer.getAccountKey() : null)
                    .accountNumber(customer.getAccountNumber() != null ? customer.getAccountNumber() : null)
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .ssn(customer.getSsn())
                    .dateOfBirth(customer.getDateOfBirth())
                    .email(customer.getEmail())
                    .phoneNumber(customer.getPhoneNumber())
                    .address(convertAddressToString(customer.getAddress()))
                    .build();
        } catch (Exception e) {
            log.error("Error while mapping customer object to entity ");
            throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        return customerEntity;
    }

    public Customer mapToDomain(CustomerEntity customerEntity) {
        Customer customer;
        if(isEmpty(customerEntity)) {
            throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR, "Entity object to be mapped is null ");
        }
        try {
            customer = Customer.builder()
                    .accountKey(customerEntity.getAccountKey() != null ? customerEntity.getAccountKey() : null)
                    .accountNumber(customerEntity.getAccountNumber() != null ? customerEntity.getAccountNumber() : null)
                    .firstName(customerEntity.getFirstName())
                    .lastName(customerEntity.getLastName())
                    .email(customerEntity.getEmail())
                    .ssn(customerEntity.getSsn())
                    .dateOfBirth(customerEntity.getDateOfBirth())
                    .phoneNumber(customerEntity.getPhoneNumber())
                    .address(convertStringToAddress(customerEntity.getAddress()))
                    .build();
        } catch (Exception e) {
            log.error("Error while mapping entity object to customer domain ");
            throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        return customer;
    }

    private String convertAddressToString(Address address) {
        try {
            if(isEmpty(address)) {
                throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Address object to be mapped is null ");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(address);
        } catch (Exception e) {
            log.error("Error converting address to string", e);
            throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error converting address to string");
        }
    }

    private Address convertStringToAddress(String address) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(address, Address.class);
        } catch (Exception e) {
            log.error("Error converting address string to address", e);
            throw new CustomerDataException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error converting address string to address");
        }
    }
}
