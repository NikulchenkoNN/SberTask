package ru.task.bankAPI.server.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.services.Service;
import ru.task.bankAPI.services.ServiceImpl;
import ru.task.bankAPI.services.dto.Converter;
import ru.task.bankAPI.services.dto.ConverterImpl;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.services.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateUserHandler implements HttpHandler {
    Converter converter = new ConverterImpl();
    Service service = new ServiceImpl();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int code;

        User user = (User) converter.jsonToObject(exchange.getRequestBody(), User.class);
        user = service.createUser(user.getName());

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
