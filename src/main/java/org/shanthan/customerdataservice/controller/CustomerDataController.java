package org.shanthan.customerdataservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.shanthan.customerdataservice.model.*;
import org.shanthan.customerdataservice.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.shanthan.customerdataservice.util.CustomerConstants.CUSTOMER_UPDATE_SUCCESSFUL;
import static org.shanthan.customerdataservice.util.CustomerConstants.DELETE_SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Slf4j
@RequestMapping("/")
public class CustomerDataController {

    private final CustomerService customerService;

    public CustomerDataController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @PostMapping("v1/add/customer")
    public ResponseEntity<CustomerAccDataResponse> addCustomer(@RequestBody @Valid Customer customer) {
        log.info("Adding customer with firstName : {}", customer.getFirstName());

        CustomerAccDataResponse responseBody = customerService.saveCustomer(customer);

        return ResponseEntity.status(CREATED).body(responseBody);
    }

    @PutMapping("v1/customer/update/address")
    public ResponseEntity<DefaultSuccessResponse> updateCustomerAddress(
            @RequestBody @Valid UpdateAddress updateAddress) {
        log.info("Updating customer address with account key : {}", updateAddress.getAccountKey());

        Customer customer = customerService.updateCustomerAddress(updateAddress.getAccountKey(),
                updateAddress.getAddress());

        DefaultSuccessResponse body = DefaultSuccessResponse.builder()
                .accountKey(customer.getAccountKey())
                .message(CUSTOMER_UPDATE_SUCCESSFUL)
                .build();

        return ResponseEntity.ok(body);
    }

    @DeleteMapping("v1/delete/customer/{accountKey}")
    public ResponseEntity<DefaultSuccessResponse> deleteCustomer(@PathVariable("accountKey") String accountKey) {
        log.info("Deleting customer with account key : {}", accountKey);

        customerService.deleteCustomer(accountKey);

        DefaultSuccessResponse body = DefaultSuccessResponse.builder()
                .accountKey(accountKey)
                .message(DELETE_SUCCESS_MESSAGE)
                .build();

        return ResponseEntity.ok(body);
    }

    @GetMapping("v1/get/customer/{accountKey}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("accountKey") String accountKey) {
        log.info("Getting customer with account key : {}", accountKey);

        Customer body = customerService.getCustomerDetails(accountKey);

        return ResponseEntity.ok(body);
    }

    @GetMapping("v1/get/customers/by/firstName/{firstName}")
    public ResponseEntity<List<Customer>> getCustomerByFirstName(@PathVariable("firstName") String firstName) {
        log.info("Getting customer list by first name : {}", firstName);

        List<Customer> customers = customerService.getCustomersByFirstName(firstName);

        return ResponseEntity.ok(customers);
    }

    @GetMapping("v1/get/customers/by/lastName/{lastName}")
    public ResponseEntity<List<Customer>> getCustomerByLastName(@PathVariable("lastName") String lastName) {
        log.info("Getting customer list by last name : {}", lastName);

        List<Customer> customers = customerService.getCustomersByLastName(lastName);

        return ResponseEntity.ok(customers);
    }

}
