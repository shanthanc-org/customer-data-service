package org.shanthan.customerdataservice.util;

public class CustomerConstants {

    private CustomerConstants() {
        //implicit constructor
    }

    public static final String SEED_CHARACTERS = "0123456789";

    public static final String ACC_NUM_SEED = "6";

    public static final int ACC_KEY_LEN = 9;

    public static final int ACC_NUM_LEN = 12;

    public static final String EMAIL_REGEXP =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final String PHONE_REGEXP =
            "^(\\d{3}-\\d{3}-\\d{4}|\\(\\d{3}\\) \\d{3}-\\d{4}|\\d{3}\\.\\d{3}\\.\\d{4}|\\d{10})$";

}
