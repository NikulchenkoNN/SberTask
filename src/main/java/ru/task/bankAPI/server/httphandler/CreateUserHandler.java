package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.*;
import ru.task.bankAPI.services.dto.Converter;
import ru.task.bankAPI.services.dto.ConverterImpl;
import ru.task.bankAPI.model.User;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateUserHandler implements HttpHandler {
    private Converter converter = new ConverterImpl();
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int code;

        User user = (User) converter.jsonToObject(exchange.getRequestBody(), User.class);
        user = userService.createUser(user.getName());

        if (user.getId() != null) {
            response = converter.objectToJSON(user);
            code = 200;
        } else {
            response = "User with name " + user.getName() + " already exits";
            code = 406;
        }
        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
