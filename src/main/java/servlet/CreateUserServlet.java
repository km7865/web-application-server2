package servlet;

import db.DataBase;
import lombok.extern.slf4j.Slf4j;
import model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebServlet("/user/create")
public class CreateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User(req.getParameter("userId")
                , req.getParameter("password")
                , req.getParameter("name")
                , req.getParameter("email"));
        log.debug("user: {}", user);

        DataBase.addUser(user);
        resp.sendRedirect("/index.jsp");
    }
}
