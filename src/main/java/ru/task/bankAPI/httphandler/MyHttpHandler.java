package ru.task.bankAPI.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.cardnumber.CardNumber;
import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.model.User;
import ru.task.bankAPI.service.UserCardService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;


public class MyHttpHandler implements HttpHandler {
    private final UserDao userDao = new UserDaoImpl();
    private final CardDao cardDao = new CardDaoImpl(userDao);

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;
        int status;
        String query;
        String rawQuery;
        switch (exchange.getRequestURI().getPath()) {
            case "/create.user":
                rawQuery = exchange.getRequestURI().getRawQuery();
                query = rawQuery.substring(rawQuery.lastIndexOf("=") + 1);
                User user = userDao.createUser(query);
                if (user != null) {
                    response = "User " + user.getName() + " created";
                    status = 200;
                } else {
                    response = "User with name " + query + " already exist";
                    status = 404;
                }
                break;

            case "/create.card":
                String cardNumber = CardNumber.getInstance().createNumber();
                rawQuery = exchange.getRequestURI().getRawQuery();
                query = rawQuery.substring(rawQuery.lastIndexOf("=") + 1);
                UserCardService.addCardToUser(userDao.findUserByName(query).getName(), cardDao.createCard(cardNumber).getNumber());
                response = cardNumber + " created for user " + query;
                status = 200;
                break;

            case "/show.users":
                Set<User> userSet = userDao.getUsers();
                if (!userSet.isEmpty()) {
                    response = userDao.getUsers().toString();
                    status = 200;
                } else {
                    response = "No users to show";
                    status = 404;
                }
                break;

            case "/update.balance":
                rawQuery = exchange.getRequestURI().getRawQuery();
                String[] strings = rawQuery.split("&");
                String name = Arrays.stream(strings[0].split("=")).reduce((first, second) -> second).get();
                String card = Arrays.stream(strings[1].split("=")).reduce((first, second) -> second).get();
                String cashStr = Arrays.stream(strings[2].split("=")).reduce((first, second) -> second).get();
                double cash = Double.parseDouble(cashStr);
                cardDao.updateCardBalance(name, card, cash);
                response = "Card " + card + " updated balance on " + cash;
                status = 200;
                break;

            default:
                response = "";
                status = 404;
        }
        exchange.sendResponseHeaders(status, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
