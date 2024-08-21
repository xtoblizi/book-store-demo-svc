package com.example.demo.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonConverter {
    public static <T> String toJson(T obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        }catch (JsonProcessingException ex) {
            log.error(" Failed to convert object to json: {}", ex.getMessage());
        }
        return "";
    }

    public static <T> T toObj(String is, Class<T> objClass) {
        T obj = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            obj = mapper.readValue(is, objClass);
        } catch (IOException ex) {
            log.error("Failed to convert json to object: {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
        return obj;
    }
}
