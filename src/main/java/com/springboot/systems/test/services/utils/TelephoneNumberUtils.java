package com.springboot.systems.test.services.utils;

import com.springboot.systems.test.services.exceptions.ValidationFailedException;
import org.apache.commons.lang.StringUtils;

public class TelephoneNumberUtils {
    public static final int MINIMUM_NUMBER_LENGTH = 9;
    public static final String INVALID_TELEPHONE_NUMBER = "Invalid Telephone Number: ";
    public static final String TELEPHONE_NUMBER_MUST_BE_OF_FORMAT = ". Telephone Numbers must be of Format: 07XX123456 OR 7XX123456 OR 2567XX123456";

    private TelephoneNumberUtils() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static String getValidTelephoneNumber(String telephone) throws ValidationFailedException {
        if (telephone != null && !telephone.isEmpty()) {
            String tel = telephone.trim().replaceAll(" ", "").replaceAll("\\+", "")
                    .replace("-", "").replace(",", "");
            if (StringUtils.isNumeric(tel)) {
                if (tel.length() == 12) {
                    return tel;
                }

                if (tel.length() == 10 && tel.startsWith("0")) {
                    return tel.replaceFirst("0", "256");
                }

                if (tel.length() == 9 && !tel.startsWith("0")) {
                    return "256" + tel;
                }
            }
        }

        throw new ValidationFailedException(INVALID_TELEPHONE_NUMBER + telephone + TELEPHONE_NUMBER_MUST_BE_OF_FORMAT);
    }

    public static String getValidPersonalUserUploadTelephoneNumber(String telephone, String rowNumber) throws ValidationFailedException {
        String defaultPersonalNumber;
        if (telephone != null && !telephone.isEmpty()) {
            defaultPersonalNumber = telephone.trim().replaceAll(" ", "").replaceAll("\\+", "").replace("-", "").replace(",", "");
            if (StringUtils.isNumeric(defaultPersonalNumber)) {
                if (defaultPersonalNumber.length() == 12) {
                    return defaultPersonalNumber;
                }

                if (defaultPersonalNumber.length() == 10 && defaultPersonalNumber.startsWith("0")) {
                    return defaultPersonalNumber.replaceFirst("0", "256");
                }

                if (defaultPersonalNumber.length() == 9) {
                    return "256" + defaultPersonalNumber;
                }
            }
        } else if (telephone == null) {
            defaultPersonalNumber = "25671200";
            if (rowNumber.length() == 1) {
                return defaultPersonalNumber + "000" + rowNumber;
            }

            if (rowNumber.length() == 2) {
                return defaultPersonalNumber + "00" + rowNumber;
            }

            if (rowNumber.length() == 3) {
                return defaultPersonalNumber + "0" + rowNumber;
            }

            if (rowNumber.length() == 4) {
                return defaultPersonalNumber + rowNumber;
            }
        }

        throw new ValidationFailedException(INVALID_TELEPHONE_NUMBER + telephone + TELEPHONE_NUMBER_MUST_BE_OF_FORMAT);
    }

    public static String getValidMerchantUserUploadTelephoneNumber(String telephone, String rowNumber) throws ValidationFailedException {
        String defaultPersonalNumber;
        if (telephone != null && !telephone.isEmpty()) {
            defaultPersonalNumber = telephone.trim().replaceAll(" ", "").replaceAll("\\+", "").replace("-", "").replace(",", "");
            if (StringUtils.isNumeric(defaultPersonalNumber)) {
                if (defaultPersonalNumber.length() == 12) {
                    return defaultPersonalNumber;
                }

                if (defaultPersonalNumber.length() == 10 && defaultPersonalNumber.startsWith("0")) {
                    return defaultPersonalNumber.replaceFirst("0", "256");
                }

                if (defaultPersonalNumber.length() == 9) {
                    return "256" + defaultPersonalNumber;
                }
            }
        } else if (telephone == null) {
            defaultPersonalNumber = "25671100";
            if (rowNumber.length() == 1) {
                return defaultPersonalNumber + "000" + rowNumber;
            }

            if (rowNumber.length() == 2) {
                return defaultPersonalNumber + "00" + rowNumber;
            }

            if (rowNumber.length() == 3) {
                return defaultPersonalNumber + "0" + rowNumber;
            }

            if (rowNumber.length() == 4) {
                return defaultPersonalNumber + rowNumber;
            }
        }

        throw new ValidationFailedException(INVALID_TELEPHONE_NUMBER + telephone + TELEPHONE_NUMBER_MUST_BE_OF_FORMAT);
    }
}
