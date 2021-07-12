package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardService;
import ru.task.bankAPI.services.CardServiceImpl;
import ru.task.bankAPI.services.UserServiceImpl;
import ru.task.bankAPI.services.dto.Converter;
import ru.task.bankAPI.services.dto.ConverterImpl;
import ru.task.bankAPI.services.dto.UpdateBalanceDto;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

public class UpdateBalanceHandler implements HttpHandler {
    private Converter converter = new ConverterImpl();
    private CardService cardService = CardServiceImpl.getInstance();
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int code;

        UpdateBalanceDto o = (UpdateBalanceDto) converter.jsonToObject(exchange.getRequestBody(), UpdateBalanceDto.class);
        String userName = o.getName();
        User user = userService.findUserByName(userName);

        if (user != null) {
            Long userId = user.getId();
            Long cardId = o.getCardId();
            BigDecimal cash = o.getCash();
            cardService.updateBalance(userId, cardId, cash);
            Card card = cardService.findCardByUserId(userId);
            if (card != null) {
                response = "Card " + converter.objectToJSON(card) + " balance updated";
                code = 200;
            } else {
                response = "Card with such data not exist";
                code = 406;
            }
        } else {
            response = "User with name " + userName + " have no such card";
            code = 406;
        }

        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
