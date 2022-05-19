package webserver;

import controller.*;
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
    private Map<String, Controller> controllers;
    private HttpRequest req;
    private HttpResponse resp;

    public RequestHandler(Socket connection) {
        this.connection = connection;

        controllers = new HashMap<>();
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
    }

    @Override
    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO
            req = new HttpRequest(in);
            resp = new HttpResponse(out);
            log.debug("URL: {}", req.getPath());
            if (!controllers.containsKey(req.getPath())) {
                new AbstractController().service(req, resp);
            } else {
                controllers.get(req.getPath()).service(req, resp);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
