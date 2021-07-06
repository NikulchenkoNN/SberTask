package ru.task.bankAPI.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class DtoImpl implements Dto {
    private static final ObjectMapper mapper = new ObjectMapper();

    public String objectToJSON(Object obj) throws IOException {
        return mapper.writeValueAsString(obj);
    }

    public Object jsonToObject(InputStream inputStream, Class clazz) throws IOException {
        return mapper.readValue(inputStream, clazz);
    }
}

