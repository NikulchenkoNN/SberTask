package ru.task.bankAPI.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class UpdateBalanceDto {
    @Getter @Setter
    private String  name;
    @Getter @Setter
    private Long cardId;
    @Getter @Setter
    private BigDecimal cash;
}
