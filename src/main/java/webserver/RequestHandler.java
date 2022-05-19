package webserver;

import db.DataBase;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.*;

@Slf4j
public class RequestHandler extends Thread {

    private Socket connection;
    private HttpRequest req;
    private HttpResponse resp;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO
            // HTTP 요청 헤더 읽어서 url 분리하기
            // webapp 디렉토리에서 html파일을 body에 저장 후 응답하기
            req = new HttpRequest(in);
            resp = new HttpResponse(out);
            log.debug("URL: {}", req.getUrl());

            int contentLength = 0;
            if (req.containsHeader("Content-Length"))
                contentLength = Integer.parseInt(req.getHeader("Content-Length"));
            boolean isLogin = (req.getCookie("logined") != null && req.getCookie("logined").equals("true"));
            if (req.getUrl().startsWith("/user/create")) {
                User user = new User(req.getParameter("userId")
                        , req.getParameter("password")
                        , req.getParameter("name")
                        , req.getParameter("email"));
                DataBase.addUser(user);

                DataOutputStream dos = new DataOutputStream(out);

                resp.sendRedirect("/index.html");
            } else if (req.getUrl().equals("/user/login")) {
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
            } else if (req.getUrl().equals("/user/logout")) {
                resp.sendRedirect("/index.html");
            } else if (req.getUrl().equals("/user/list")) {
                if (isLogin) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(DataBase.findAll());
                    byte[] body = sb.toString().getBytes();
                    resp.setBody(body);
                    resp.response200Header();
                    resp.responseBody();
                } else {
                    resp.sendRedirect("/user/login.html");
                }
            } else {
                resp.forward(req.getUrl());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
