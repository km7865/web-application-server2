package servlet;

import db.DataBase;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.io.IOException;

@Slf4j
@WebServlet("/user/update")
public class UpdateUserServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userId = req.getParameter("preUserId");
        User user = new User(req.getParameter("userId")
                , req.getParameter("password")
                , req.getParameter("name")
                , req.getParameter("email"));
        log.debug("user: {}", user);

        DataBase.addUser(user);
        resp.sendRedirect("/user/list");
    }
}
