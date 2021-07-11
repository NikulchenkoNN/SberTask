package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.Service;
import ru.task.bankAPI.services.ServiceImpl;
import ru.task.bankAPI.services.dto.Converter;
import ru.task.bankAPI.services.dto.ConverterImpl;
import ru.task.bankAPI.services.dto.UpdateBalanceDto;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardService;
import ru.task.bankAPI.services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class GetBalanceHandler implements HttpHandler {
    Converter converter = new ConverterImpl();
    Service service = new ServiceImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int code;

        UpdateBalanceDto o = (UpdateBalanceDto) converter.jsonToObject(exchange.getRequestBody(), UpdateBalanceDto.class);
        String userName = o.getName();
        User user = service.findUserByName(userName);

        if (user != null) {
            Long userId = user.getId();
            Long cardId = o.getCardId();
            BigDecimal balance = service.getBalance(userId, cardId);
            response = "Balance of card " + service.findCardByUserId(userId).getNumber() + " of user "+ userName+ " " + converter.objectToJSON(balance);
            code = 200;
        } else {
            response = "User " + userName + " don't have such card";
            code = 406;
        }

        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
