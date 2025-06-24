package com.capitole.zara.products.adapter.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utilities {

    public static final String FORMAT_LOCAL_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime stringToLocalDateTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_LOCAL_DATE_TIME);
        return LocalDateTime.parse(date, formatter);
    }
}
