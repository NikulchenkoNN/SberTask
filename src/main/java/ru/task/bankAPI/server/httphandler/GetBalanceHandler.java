package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.dto.Dto;
import ru.task.bankAPI.services.dto.DtoImpl;
import ru.task.bankAPI.services.dto.UpdateBalanceDto;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardService;
import ru.task.bankAPI.services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class GetBalanceHandler implements HttpHandler {
    Dto dto = new DtoImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;

        UpdateBalanceDto o = (UpdateBalanceDto) dto.jsonToObject(exchange.getRequestBody(), UpdateBalanceDto.class);
        String userName = o.getName();
        User user = UserService.findUserByName(userName);

        if (user != null) {
            Long userId = user.getId();
            Long cardId = o.getCardId();
            BigDecimal balance = CardService.getBalance(userId, cardId);
            response = "Balance of card " + CardService.findCardByUserId(userId).getNumber() + " of user "+ userName+ " " + dto.objectToJSON(balance);
        } else {
            response = "User " + userName + " don't have such card";
        }

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
