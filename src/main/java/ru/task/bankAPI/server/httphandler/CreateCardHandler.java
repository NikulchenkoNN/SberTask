package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.dto.ConverterImpl;
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
    ConverterImpl dto = new ConverterImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        User user = (User) dto.jsonToObject(exchange.getRequestBody(), User.class);
        String userName = user.getName();
        CardNumber.getInstance();
        String cardNumber = CardNumber.createNumber();

        user = UserService.findUserByName(user.getName());
        if (user != null) {
            Long userId = user.getId();
            CardService.createCard(cardNumber);
            UserCardService.addCardToUser(userId, cardNumber);
            Set<Card> cards = CardService.getCardsByUser(userId);

            response = "User " + user.getName() + "have this cards\n" + dto.objectToJSON(cards);
        } else {
            response = "User with name " + userName + " not exist";
        }
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
