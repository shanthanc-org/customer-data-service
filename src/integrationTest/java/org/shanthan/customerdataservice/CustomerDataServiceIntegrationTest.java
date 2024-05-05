package org.shanthan.customerdataservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shanthan.customerdataservice.model.*;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.shanthan.customerdataservice.IntegrationTestConstants.*;

public class CustomerDataServiceIntegrationTest extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void givenValidCustomer_whenRequestedToAdd_returnCustAccDtRespWithStatusCreated() {

        Customer customer = Customer.builder()
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .email(TEST_EMAIL)
                .ssn(TEST_SSN)
                .phoneNumber(TEST_PHONE)
                .dateOfBirth(TEST_DOB)
                .address(Address.builder()
                        .street(TEST_STREET)
                        .city(TEST_CITY)
                        .state(TEST_STATE)
                        .zipCode(TEST_ZIPCODE)
                        .build())
                .build();

        // When & Then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(customer)
                .when()
                .post("v1/add/customer")
                .then()
                .statusCode(201)
                .body("accountKey.length()", equalTo(ACC_KEY_LEN),
                        "accountNumber.length()", equalTo(ACC_NUM_LEN))
                .extract()
                .as(CustomerAccDataResponse.class);
    }

    @Test
    public void givenValidAccKey_whenRequestedToGet_thenReturnCustomerWith200Status() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("accountKey", GET_ACC_KEY)
                .when()
                .get("v1/get/customer/{accountKey}")
                .then()
                .statusCode(200)
                .body("accountNumber", equalTo(GET_ACC_NUM))
                .extract()
                .as(Customer.class);
    }

    @Test
    public void givenValidAccKey_whenRequestedToDelete_thenReturnSuccessWith200Status() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("accountKey", DEL_ACC_KEY)
                .when()
                .delete("v1/delete/customer/{accountKey}")
                .then()
                .statusCode(200)
                .body("accountKey", equalTo(DEL_ACC_KEY))
                .body("message", equalTo(DELETE_SUCCESS_MESSAGE))
                .extract()
                .as(DefaultSuccessResponse.class);
    }

    @Test
    public void givenValidAccKeyAndAddrToUpdate_whenUpdateRequested_returnSuccessRespWithStatus200() {
        UpdateAddress updateAddress = UpdateAddress.builder()
                .accountKey(UPDATE_ACC_KEY)
                .address(UPDATED_ADDRESS)
                .build();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(updateAddress)
                .when()
                .put("v1/customer/update/address")
                .then()
                .statusCode(200)
                .body("accountKey", equalTo(UPDATE_ACC_KEY))
                .body("message", equalTo(CUSTOMER_UPDATE_SUCCESSFUL))
                .extract()
                .as(DefaultSuccessResponse.class);
    }

    @Test
    public void givenValidAccKey_whenRequestedToGetByFirstName_thenReturnCustomersWith200Status() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("firstName", REPEAT_FIRST_NAME)
                .when()
                .get("v1/get/customers/by/firstName/{firstName}")
                .then()
                .statusCode(200)
                .body("customers", hasSize(2))
                .extract()
                .as(List.class);
    }

    @Test
    public void givenValidAccKey_whenRequestedToGetByLastName_thenReturnCustomersWith200Status() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .pathParam("lastName", REPEAT_LAST_NAME)
                .when()
                .get("v1/get/customers/by/lastName/{lastName}")
                .then()
                .statusCode(200)
                .body("customers", hasSize(2))
                .extract()
                .as(List.class);
    }


}
