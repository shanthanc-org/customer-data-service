package org.shanthan.customerdataservice;

import org.shanthan.customerdataservice.model.Address;

public class IntegrationTestConstants {

    public static final String TEST_FIRST_NAME = "testFirstName";
    public static final String TEST_LAST_NAME = "testLastName";
    public static final String TEST_EMAIL = "testEmail@someDomain.com";
    public static final String TEST_PHONE = "555-555-5555";
    public static final String TEST_SSN = "333-11-2222";
    public static final String TEST_DOB = "01/31/1997";
    public static final String TEST_CITY = "testCity";
    public static final String TEST_STREET = "100 S someStreet";
    public static final String TEST_ZIPCODE = "12345";
    public static final String TEST_STATE = "IL";

    public static final int ACC_KEY_LEN = 9;

    public static final int ACC_NUM_LEN = 12;

    public static final Address UPDATED_ADDRESS = Address.builder()
            .street("231 Reagan St")
            .city("Austin")
            .state("TX")
            .zipCode("73302")
            .build();

    public static final String DEL_ACC_KEY = "540456772";
    public static final String UPDATE_ACC_KEY = "340123789";

    public static final String DELETE_SUCCESS_MESSAGE = "Customer deleted successfully";
    public static final String CUSTOMER_UPDATE_SUCCESSFUL = "Customer Address updated successfully";

    public static final String GET_ACC_KEY = "150456789";
    public static final String GET_ACC_NUM = "667890123456";

    public static final String REPEAT_FIRST_NAME = "Alice";

    public static final String REPEAT_LAST_NAME = "Brown";
}
