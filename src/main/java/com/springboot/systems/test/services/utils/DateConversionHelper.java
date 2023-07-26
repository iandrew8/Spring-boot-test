package com.springboot.systems.test.services.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * This class will help with date conversions
 */
public class DateConversionHelper {

    private DateConversionHelper() {
        throw new IllegalStateException("Utility class cannot be instantiated");
    }

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_TIME_FORMAT_V2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Responsible for converting a {@link Date} to a {@link LocalDateTime}
     *
     * @param dateToConvert, the {@link Date} to convert
     * @return the {@link LocalDateTime} version
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * Responsible for converting a {@link LocalDateTime} to a {@link Date}
     *
     * @param dateToConvert, the {@link LocalDateTime} to convert
     * @return the {@link Date} version
     */
    public static Date convertToDate(LocalDateTime dateToConvert) {
        return Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    /**
     * Responsible for converting a {@link String} to a {@link Date}
     * yyyy-MM-dd is the format used
     *
     * @param date, the {@link String} to convert
     * @return the {@link Date} version
     */
    public static Date convertToDate(String date) throws ParseException {
        Date newDate = null;

        if (StringUtils.isNotBlank(date))
            newDate = DATE_FORMAT.parse(date);

        return newDate;
    }

    /**
     * Responsible for converting a {@link String} to a {@link Date}
     * dd/MM/yyyy HH:mm:ss is the format used
     *
     * @param date, the {@link String} to convert
     * @return the {@link Date} version
     */
    public static Date convertToDateTime(String date) throws ParseException {
        Date newDate = null;

        if (StringUtils.isNotBlank(date))
            newDate = DATE_TIME_FORMAT.parse(date);

        return newDate;
    }

    /**
     * Responsible for converting a {@link String} to a {@link Date}
     * yyyy-MM-dd HH:mm:ss is the format used
     *
     * @param date, the {@link String} to convert
     * @return the {@link Date} version
     */
    public static Date convertToDateTimeV2(String date) throws ParseException {
        Date newDate = null;

        if (StringUtils.isNotBlank(date))
            newDate = DATE_TIME_FORMAT_V2.parse(date);

        return newDate;
    }

    /**
     * Converts a {@link Date} to a {@link String}
     *
     * @param date, the {@link Date} to convert
     * @return the {@link String} version
     */
    public static String convertToString(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * This method will return true if the current date is less than the new date
     *
     * @param currentDate, the current {@link Date}
     * @param newDate,     the new {@link Date}
     * @return true if the current date is less than the new date
     */
    public static boolean isCurrentDateLessThanNewDate(Date currentDate, Date newDate) {
        LocalDateTime currentLocalDateTime;

        /*Cater for scenarios if the expiry date of the client is null*/
        if (currentDate == null)
            currentLocalDateTime = LocalDateTime.now();
        else
            currentLocalDateTime = convertToLocalDateTime(currentDate);

        LocalDateTime newLocalDateTime = convertToLocalDateTime(newDate);
        return currentLocalDateTime.isBefore(newLocalDateTime);
    }

    /**
     * This method will return true if the range between the old and new date is divisible by 30 days
     *
     * @param oldDate, the old {@link Date}
     * @param newDate, the new {@link Date}
     * @return true if the range between the old and new date is divisible by 30 days
     */
    public static boolean isDateDivisibleBy30Days(Date oldDate, Date newDate) {
        /*We need to add a second to the new date so that it clocks past old date. Otherwise, 29 days will be counted
        * instead of 30*/
        if (convertToLocalDateTime(newDate).isAfter(convertToLocalDateTime(oldDate))) {
            newDate.setHours(oldDate.getHours());
            newDate.setMinutes(oldDate.getMinutes());
            newDate.setSeconds(oldDate.getSeconds()+1);
        }
        return ChronoUnit.DAYS.between(convertToLocalDateTime(oldDate), convertToLocalDateTime(newDate)) % 30 == 0;
    }

    /**
     * This method will return a new {@link Date} with the number of days added to it
     *
     * @param date, the {@link Date} to add the days to
     * @param days, the number of days to add
     * @return the new {@link Date}
     */
    public static Date addDaysToDate(Date date, int days) {
        LocalDateTime localDateTime = convertToLocalDateTime(date);
        localDateTime = localDateTime.plusDays(days);
        return convertToDate(localDateTime);
    }

}
