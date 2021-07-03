package ru.task.bankAPI.dto;

import java.io.IOException;

public interface Dto {
    <T> String fromJSONToString(T user) throws IOException;
}