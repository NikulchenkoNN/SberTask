package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.dto.Converter;
import ru.task.bankAPI.services.dto.ConverterImpl;
import ru.task.bankAPI.model.Card;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.CardService;
import ru.task.bankAPI.services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class ShowCardsHandler implements HttpHandler {
    Converter converter = new ConverterImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int code;
        User user = (User) converter.jsonToObject(exchange.getRequestBody(), User.class);
        user = UserService.findUserByName(user.getName());
        if (user != null) {
            Set<Card> cardsByUser = CardService.getCardsByUser(user.getId());
            if (!cardsByUser.isEmpty()) {
                response = converter.objectToJSON(cardsByUser);
                code = 200;
            } else {
                response = "User " + user.getName() +" has no cards";
                code = 406;
            }
        } else {
            response = "No such user to show cards";
            code = 406;
        }
        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
