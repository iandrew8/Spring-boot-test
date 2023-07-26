package com.springboot.systems.test.services.utils;

public class CustomStringUtil {

    private CustomStringUtil() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Converts a string to camel case
     *
     * @param text, the string to be converted
     * @return the camel case of the string
     */
    public static String toCamelCase(String text) {
        String[] words = text.trim().split("\\s+");
        StringBuilder camelCase = new StringBuilder();
        for (String word : words) {
            camelCase.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
                camelCase.append(word.substring(1).toLowerCase());
                camelCase.append(" ");
            }
        }
        return camelCase.toString();
    }

    /**
     * This method will take a string and trim it to remove " ", "-", "_", "&", "@" and "."
     */
    public static String trimString(String text) {
        return text.replaceAll("[\\s\\-\\_\\&\\@\\.]", "");
    }
}
