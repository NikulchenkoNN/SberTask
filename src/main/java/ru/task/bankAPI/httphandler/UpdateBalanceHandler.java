package ru.task.bankAPI.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.dto.Dto;
import ru.task.bankAPI.dto.DtoImpl;
import ru.task.bankAPI.dto.UpdateBalanceDto;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.service.CardService;
import ru.task.bankAPI.service.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class UpdateBalanceHandler implements HttpHandler {
    Dto dto = new DtoImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UpdateBalanceDto o = (UpdateBalanceDto) dto.jsonToObject(exchange.getRequestBody(), UpdateBalanceDto.class);
        String userName = o.getName();
        User user = UserService.findUserByName(userName);
        Long userId = user.getId();
        Long cardId = o.getCardId();
        BigDecimal cash = o.getCash();
        CardService.updateBalance(userId, cardId, cash);
        BigDecimal balance = CardService.getBalance(userId, cardId);
        Card card = CardService.findCardById(userId);
        String response =  dto.objectToJSON(card);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
