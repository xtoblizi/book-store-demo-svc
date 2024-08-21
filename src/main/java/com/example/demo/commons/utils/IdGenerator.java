package com.example.demo.commons.utils;


import com.example.demo.commons.exceptions.UtilException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private IdGenerator() throws UtilException {
        throw new UtilException("Cannot instantiate this util class: IdGenerator");
    }

    private static AtomicInteger i = new AtomicInteger(0);
    private static int getValue(){
        return i.get()>=9999 ? i.getAndSet(0) : i.getAndIncrement();
    }

    public static String getTimeBasedId(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"))+String.format("%04d",getValue());
    }

    public static String getDateTimeOnly(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));
    }

    public static String getUuidBasedId(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static String getUniformSizeTimeBasedId(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmmss"))+String.format("%04d",getValue());
    }

    public static String getRandomAlphaNumeric(int size){
        String alphanumeric = "qwertyuiopasdfghjklzxcvbnm1234567890";
        int alphaNumericLen = alphanumeric.length();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<size; i++){
            builder.append(alphanumeric.charAt(new SecureRandom().nextInt(alphaNumericLen)));
        }
        return builder.toString();
    }

    public static String randomDigitsFromTime(int size){
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
        size = Math.min(time.length(),size);
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<size;i++){
            builder.append(time.charAt(new SecureRandom().nextInt(time.length())));
        }
        return builder.toString();
    }
}
