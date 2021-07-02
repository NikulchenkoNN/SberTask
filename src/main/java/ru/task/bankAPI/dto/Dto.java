package ru.task.bankAPI.dto;

import java.io.IOException;

public interface Dto {
    <T> String fromDbToString(T user) throws IOException;
}