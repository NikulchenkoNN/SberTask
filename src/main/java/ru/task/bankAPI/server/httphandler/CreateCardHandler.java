package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.*;
import ru.task.bankAPI.services.dto.Converter;
import ru.task.bankAPI.services.dto.ConverterImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class CreateCardHandler implements HttpHandler {
    private Converter converter = new ConverterImpl();
    private CardService cardService = CardServiceImpl.getInstance();
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int code;

        User user = (User) converter.jsonToObject(exchange.getRequestBody(), User.class);
        String userName = user.getName();
        CardNumber.getInstance();
        String cardNumber = CardNumber.createNumber();

        user = userService.findUserByName(user.getName());
        if (user != null) {
            Long userId = user.getId();
            cardService.createCard(cardNumber);
            cardService.addCardToUser(userId, cardNumber);
            Set<Card> cards = cardService.getCardsByUser(userId);

            response = "User " + user.getName() + "have this cards\n" + converter.objectToJSON(cards);
            code = 200;
        } else {
            response = "User with name " + userName + " not exist";
            code = 406;
        }
        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
