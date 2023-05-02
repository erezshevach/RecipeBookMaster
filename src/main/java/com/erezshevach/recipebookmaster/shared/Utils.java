package com.erezshevach.recipebookmaster.shared;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class Utils {
    private final Random RANDOM = new SecureRandom();


    public String generateRecipePid(int length, String name) {
        String nameSample = name.replace(" ", "").substring(0,(Math.min(10, name.length()))).toLowerCase();
        return nameSample + "_" + generateRandomString(length);
    }

    public String generateProcessPid(int length) {
        return "P" + generateRandomString(length);
    }

    public String generateComponentPid(int length) {
        return "C" + generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
            sb.append((ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length()))));
        }
        return new String(sb);
    }

}
