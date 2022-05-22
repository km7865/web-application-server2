package controller;

import db.DataBase;
import lombok.extern.slf4j.Slf4j;
import model.User;
import util.HttpRequest;
import util.HttpResponse;

@Slf4j
public class CreateUserController extends AbstractController {
    @Override
    public void doPost(HttpRequest req, HttpResponse resp) {
        User user = new User(req.getParameter("userId")
                , req.getParameter("password")
                , req.getParameter("name")
                , req.getParameter("email"));
        log.debug("user: {}", user);

        DataBase.addUser(user);
        resp.sendRedirect("/index.html");
    }
}
