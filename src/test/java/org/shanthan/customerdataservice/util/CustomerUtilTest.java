package org.shanthan.customerdataservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerUtilTest {

    @Test
    void generateAccountKey() {
        String accountKey = CustomerUtil.generateAccountKey();
        assertNotNull(accountKey);
        assertEquals(9, accountKey.length());
    }

    @Test
    void generateAccountNumber() {
        String accountNumber = CustomerUtil.generateAccountNumber();
        assertNotNull(accountNumber);
        assertEquals(12, accountNumber.length());
    }

    @Test
    void isValidDob() {
        assertTrue(CustomerUtil.isValidDob("01-01-1990"));
        assertFalse(CustomerUtil.isValidDob("01-02-19902"));
        assertFalse(CustomerUtil.isValidDob("13-03-1990"));
        assertFalse(CustomerUtil.isValidDob("safwr3qrq3r"));
    }
}