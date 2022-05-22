package controller;

import db.DataBase;
import model.User;
import util.HttpRequest;
import util.HttpResponse;

public class LoginController extends AbstractController {
    @Override
    public void doPost(HttpRequest req, HttpResponse resp) {
        User user = DataBase.findUserById(req.getParameter("userId"));
        if (user == null) {
            resp.forward("/user/login_failed.html");
            return;
        }

        if (user.getPassword().equals(req.getParameter("password"))) {
            resp.addHeader("Set-Cookie", "logined=true;");
            resp.sendRedirect("/index.html");
        } else {
            resp.forward("/user/login_failed.html");
        }
    }

}
