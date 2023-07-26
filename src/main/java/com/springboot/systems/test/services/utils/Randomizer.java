package com.springboot.systems.test.services.utils;

import java.util.Random;

public class Randomizer {

    private Randomizer() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            char randomChar = chars.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
