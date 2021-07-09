package ru.task.bankAPI.services.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConverterImpl implements Converter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public String objectToJSON(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public Object jsonToObject(InputStream inputStream, Class clazz) throws IOException {
        return mapper.readValue(inputStream, clazz);
    }
}

