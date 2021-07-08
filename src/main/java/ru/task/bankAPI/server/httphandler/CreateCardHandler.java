package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.dto.DtoImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardNumber;
import ru.task.bankAPI.services.CardService;
import ru.task.bankAPI.services.UserCardService;
import ru.task.bankAPI.services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class CreateCardHandler implements HttpHandler {
    DtoImpl dto = new DtoImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        User user = (User) dto.jsonToObject(exchange.getRequestBody(), User.class);

        CardNumber.getInstance();
        String cardNumber = CardNumber.createNumber();
        CardService.createCard(cardNumber);

        Long userId = UserService.findUserByName(user.getName()).getId();
        UserCardService.addCardToUser(userId, cardNumber);

        Set<Card> cards = CardService.getCardsByUser(userId);

        String response = "User " +user.getName() + "have this cards\n" + dto.objectToJSON(cards);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}