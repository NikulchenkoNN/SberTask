package ru.task.bankAPI.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.cardnumber.CardNumber;
import ru.task.bankAPI.dto.DtoImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.service.CardService;
import ru.task.bankAPI.service.UserCardService;
import ru.task.bankAPI.service.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateCardHandler implements HttpHandler {
    DtoImpl dto = new DtoImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        User user = (User) dto.jsonToObject(exchange.getRequestBody(), User.class);
        String cardNumber = CardNumber.getInstance().createNumber();
        CardService.createCard(cardNumber);
        Long userId = UserService.findUserByName(user.getName()).getId();
        UserCardService.addCardToUser(userId, cardNumber);
        Card card = CardService.findCardById(userId);
        String response = dto.objectToJSON(card);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
