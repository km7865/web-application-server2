package controller;

import db.DataBase;
import model.User;
import util.HttpRequest;
import util.HttpResponse;

import java.util.Collection;
import java.util.List;

public class ListUserController extends AbstractController {
    @Override
    public void doGet(HttpRequest req, HttpResponse resp) {
        if (isLogin(req.getCookie("logined"))) {
            StringBuilder sb = new StringBuilder();
            Collection<User> users = DataBase.findAll();
            sb.append("<table border='1'>");
            for (User user : users) {
                sb.append("<tr>");
                sb.append("<td>" + user.getUserId() + "</td>");
                sb.append("<td>" + user.getName() + "</td>");
                sb.append("<td>" + user.getEmail() + "</td>");
                sb.append("</tr>");
            }
            sb.append("</table>");

            byte[] body = sb.toString().getBytes();
            resp.forwardBody(body);
        } else {
            resp.sendRedirect("/user/login.html");
        }
    }

    boolean isLogin(String logined) {
        return logined != null
                && logined.equals("true");
    }
}
