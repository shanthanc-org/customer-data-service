package org.shanthan.customerdataservice.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.shanthan.customerdataservice.exception.CustomerDataException;
import org.shanthan.customerdataservice.mapper.CustomerMapper;
import org.shanthan.customerdataservice.model.Address;
import org.shanthan.customerdataservice.model.Customer;
import org.shanthan.customerdataservice.model.CustomerAccDataResponse;
import org.shanthan.customerdataservice.repository.CustomerEntity;
import org.shanthan.customerdataservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.shanthan.customerdataservice.util.CustomerConstants.CUSTOMER_WITH_ACC_KEY_DOES_NOT_EXIST;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.ObjectUtils.*;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerAccDataResponse saveCustomer(Customer customer) {
        if (isEmpty(customer)) {
            log.error("Customer object null or empty in service call to save customer");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR,
                    "Customer object cannot be null or empty ");
        }
        CustomerEntity customerEntity = customerMapper.mapToEntity(customer);
        CustomerEntity result;
        try {
            result = customerRepository.saveAndFlush(customerEntity);
            return CustomerAccDataResponse.builder()
                    .accountKey(result.getAccountKey())
                    .accountNumber(result.getAccountNumber())
                    .build();
        } catch (Exception e) {
            log.error("Error saving customer -> {} ", e.getMessage());
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public void deleteCustomer(String accountKey) {
        if (isEmpty(accountKey)) {
            log.error("Account key is null or empty in service call to delete customer");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "Account key cannot be null or empty");
        }
        if (!customerRepository.existsById(accountKey)) {
            log.error(CUSTOMER_WITH_ACC_KEY_DOES_NOT_EXIST, accountKey);
            return;
        }
        try {
            customerRepository.deleteById(accountKey);
        } catch (Exception e) {
            log.error("Error deleting customer -> {} ", e.getMessage());
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public Customer getCustomerDetails(String accountKey) {
        if (isEmpty(accountKey)) {
            log.error("Account key is null or empty in service call to retrieve customer details");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "Account key cannot be null or empty");
        }
        if (!customerRepository.existsById(accountKey)) {
            log.error(CUSTOMER_WITH_ACC_KEY_DOES_NOT_EXIST, accountKey);
            return Customer.builder().build();
        }
        CustomerEntity entity;
        Customer result;
        try {
            entity = customerRepository.getCustomerEntityByAccountKey(accountKey);
            result = customerMapper.mapToDomain(entity);
        } catch (Exception e) {
            log.error("Error retrieving customer -> {} ", e.getMessage());
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
        return result;
    }

    public List<Customer> getCustomersByFirstName(String firstName) {
        if (isEmpty(firstName)) {
            log.error("First name is null or empty in service call to get customers by first name");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "First name cannot be null or empty");
        }
        List<Customer> result = new ArrayList<>();
        try {
            List<CustomerEntity> entities = customerRepository.findByFirstName(firstName);
            entities.forEach(entity -> result.add(customerMapper.mapToDomain(entity)));
            return result;
        } catch (Exception e) {
            log.error("Error retrieving customers by first name -> {} ", e.getMessage());
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public List<Customer> getCustomersByLastName(String lastName) {
        if (isEmpty(lastName)) {
            log.error("Last name is null or empty in service call to get customers by first name");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "Last name cannot be null or empty");
        }
        List<Customer> result = new ArrayList<>();
        try {
            List<CustomerEntity> entities = customerRepository.findByLastName(lastName);
            entities.forEach(entity -> result.add(customerMapper.mapToDomain(entity)));
            return result;
        } catch (Exception e) {
            log.error("Error retrieving customers by last name -> {} ", e.getMessage());
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @Transactional
    public Customer updateCustomerAddress(String accountKey, Address address) {
        if(isEmpty(accountKey)) {
            log.error("Account key is null or empty in service call to update customer address");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "Account key cannot be null or empty");
        }

        if(isEmpty(address)) {
            log.error("Address is null or empty in service call to update customer address");
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "Address cannot be null or empty");
        }

        if (!customerRepository.existsById(accountKey)) {
            log.error(CUSTOMER_WITH_ACC_KEY_DOES_NOT_EXIST, accountKey);
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, "Cusomer with account key " + accountKey +
                    " does not exist");
        }

        try {
            CustomerEntity entity = customerRepository.getCustomerEntityByAccountKey(accountKey);
            Customer customer = customerMapper.mapToDomain(entity);
            customer.setAddress(address);
            CustomerEntity updatedEntity =
                    customerRepository.saveAndFlush(customerMapper.mapToEntity(customer));
            return customerMapper.mapToDomain(updatedEntity);
        } catch (Exception e) {
            log.error("Error updating customer -> {} ", e.getMessage());
            throw new CustomerDataException(INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
