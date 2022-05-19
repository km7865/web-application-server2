package controller;

import db.DataBase;
import model.User;
import util.HttpRequest;
import util.HttpResponse;

public class CreateUserController extends AbstractController {
    @Override
    void doPost(HttpRequest req, HttpResponse resp) {
        User user = new User(req.getParameter("userId")
                , req.getParameter("password")
                , req.getParameter("name")
                , req.getParameter("email"));

        DataBase.addUser(user);
        resp.sendRedirect("/index.html");
    }
}
