package ru.task.bankAPI.services.dto;

import java.io.IOException;
import java.io.InputStream;

public interface Converter {
    String objectToJSON(Object obj) throws IOException;
    Object jsonToObject(InputStream inputStream, Class clazz) throws IOException;
}
