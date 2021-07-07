package ru.task.bankAPI.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.dto.DtoImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.service.CardService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ShowCartsHandler implements HttpHandler {
    DtoImpl dto;

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        User user = (User) dto.jsonToObject(exchange.getRequestBody(), User.class);
        Set<Card> cardsByUser = CardService.getCardsByUser(user.getId());
        String response = dto.objectToJSON(cardsByUser);
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
