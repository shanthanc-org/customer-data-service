package org.shanthan.customerdataservice.util;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

import static org.shanthan.customerdataservice.util.CustomerConstants.*;

@Service
public class CustomerUtil {

    public static String generateAccountKey() {
        int length = ACC_KEY_LEN - 1;
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length + 1);

        int firstDigit = 1 + random.nextInt(5);
        sb.append(firstDigit);
        for (int i = 0; i < length; i++) {
            sb.append(SEED_CHARACTERS.charAt(random.nextInt(SEED_CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static String generateAccountNumber() {
        int length = ACC_NUM_LEN - 1;
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length + 1);
        sb.append(ACC_NUM_SEED);
        for (int i = 0; i < length; i++) {
            sb.append(SEED_CHARACTERS.charAt(random.nextInt(SEED_CHARACTERS.length())));
        }
        return sb.toString();
    }
}
