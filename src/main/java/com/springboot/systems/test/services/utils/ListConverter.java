package com.springboot.systems.test.services.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for converting lists and sets into well formatted strings with commas.
 * It is also responsible for converting comma-separated strings into lists
 * @author ttc
 */
public class ListConverter {

    private ListConverter() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    /**
     * Convert a List<String> to a comma-separated String
     * @param stringList, e.g. ["one", "two", "three"]
     * @return comma-separated String, e.g. "one, two, three"
     */
    public static String ListToStringConverter(List<String> stringList) {
        if(!stringList.isEmpty()) {
            return StringUtils.join(stringList, ", ");
        }else {
            return "Empty";
        }

    }

    /**
     * Convert a comma-separated String to List<String>
     * @param commaSeparatedString, e.g. "one, two, three"
     * @return List<String>
     */
    public static List<String> StringToListConverter(String commaSeparatedString) {
        return Arrays.asList(commaSeparatedString.split(","));
    }

}
