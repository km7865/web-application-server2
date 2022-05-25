package servlet;

import db.DataBase;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));
        RequestDispatcher rd = req.getRequestDispatcher("/user/login_failed.jsp");
        if (user == null) {
            rd.forward(req, resp);
            return;
        }

        if (user.getPassword().equals(req.getParameter("password"))) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("/user/list");
        } else {
            rd.forward(req, resp);
        }
    }
}
