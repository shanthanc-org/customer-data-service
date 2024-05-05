package org.shanthan.customerdataservice.util;

public class CustomerConstants {

    private CustomerConstants() {
        //implicit constructor
    }

    public static final String SEED_CHARACTERS = "0123456789";

    public static final String ACC_NUM_SEED = "6";

    public static final int ACC_KEY_LEN = 9;

    public static final int ACC_NUM_LEN = 12;

    public static final String NAME_REGEXP = "^[A-Za-zÀ-ÖØ-öø-ÿ]+([ '-][A-Za-zÀ-ÖØ-öø-ÿ]+)*$";

    public static final String EMAIL_REGEXP =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final String PHONE_REGEXP =
            "^(\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}\\.\\d{3}\\.\\d{4}|\\d{10})$";

    public static final String SSN_REGEXP = "^\\d{3}-\\d{2}-\\d{4}$";

    public static final String ACC_KEY_REGEXP = "^[1-5][0-9]{8}$";

    public static final String ZIPCODE_REGEXP = "^[0-9]{5}(-[0-9]{4})?$";

    public static final String DATE_OF_BIRTH_PATTERN = "MM/dd/yyyy";

    public static final String CUSTOMER_WITH_ACC_KEY_DOES_NOT_EXIST = "Customer with account key {} does not exist";

    public static final String CUSTOMER_UPDATE_SUCCESSFUL = "Customer Address updated successfully";

    public static final String DELETE_SUCCESS_MESSAGE = "Customer deleted successfully";

    public static final String SPECIFIC_CAUSE = "specificCause";

    public static final String CODE = "code";

    public static final String PROBLEM_DETAIL = "problemDetail";

    public static final String METHOD = "method";


}
