package com.meli.sellerapi.domain.utils;

import com.meli.sellerapi.domain.exceptions.InvalidDateException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTools {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);

    public static Date getDateTwoWeeksAgoFromNow() {
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        calendar.add(Calendar.DATE, -14); //2 weeks
        return calendar.getTime();
    }

    public static boolean dateIsAfterOrEqualToDate(Date compared, Date referenceDate) {
        return compared.after(referenceDate) || dateEquals(compared, referenceDate);
    }

    public static boolean dateEquals(Date compared, Date referenceDate) {
        return compared.equals(referenceDate);
    }



    public static Date formatStringToDate(String stringDate) throws InvalidDateException {
        try {
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            throw new InvalidDateException("invalid date format");
        }
    }
}
