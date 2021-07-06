package ru.task.bankAPI.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.task.bankAPI.dao.CardDao;
import ru.task.bankAPI.dao.CardDaoImpl;
import ru.task.bankAPI.dao.UserDao;
import ru.task.bankAPI.dao.UserDaoImpl;
import ru.task.bankAPI.service.UserService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class MyHttpHandler implements HttpHandler {
    private final UserDao userDao = new UserDaoImpl();
    private final CardDao cardDao = new CardDaoImpl(userDao);
    private final UserService service = new UserService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
//        String response;
//        int status;
//        switch (exchange.getRequestURI().getPath()) {
//            case "/createUser":
//
//                break;
//
//            case "/createCard":
//
//                break;
//
//            case "/showUsers":
//
//                break;
//
//            case "/updateBalance":
//
//                break;
//
////            case "/update.balance":
////                rawQuery = exchange.getRequestURI().getRawQuery();
////                String[] strings = rawQuery.split("&");
////                String name = Arrays.stream(strings[0].split("=")).reduce((first, second) -> second).get();
////                String card = Arrays.stream(strings[1].split("=")).reduce((first, second) -> second).get();
////                String cashStr = Arrays.stream(strings[2].split("=")).reduce((first, second) -> second).get();
////                double cash = Double.parseDouble(cashStr);
////                cardDao.updateCardBalance(userDao., card, cash);
////                response = "Card " + card + " updated balance on " + cash;
////                status = 200;
////                break;
//
//            default:
//                response = "";
//                status = 404;
//        }
//        exchange.sendResponseHeaders(status, response.length());
//        OutputStream os = exchange.getResponseBody();
//        os.write(response.getBytes(StandardCharsets.UTF_8));
//        os.close();
    }
}
