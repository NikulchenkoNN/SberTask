package ru.task.bankAPI.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class DtoImpl implements Dto {
    public String fromJSONToString(Object o) throws IOException {
        StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(writer, o);
        return writer.toString();
    }
}
