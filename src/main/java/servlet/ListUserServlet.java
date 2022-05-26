package servlet;

import db.DataBase;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@WebServlet("/user/list")
public class ListUserServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, ServletException, ServletException {
        if (req.getSession().getAttribute("user") != null) {
            req.setAttribute("users", DataBase.findAll());
            RequestDispatcher rd = req.getRequestDispatcher("/user/list.jsp");
            rd.forward(req, resp);
        } else {
            resp.sendRedirect("/index.jsp");
        }
    }
}
