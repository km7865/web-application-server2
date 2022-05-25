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
    private HttpRequest req;
    private HttpResponse resp;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO
            req = new HttpRequest(in);
            resp = new HttpResponse(out);
            String url = req.getPath();
            log.debug("URL: {}", url);

            Controller controller = RequestMapping.getController(url);
            if (controller == null) {
                resp.forward("/index.html");
            } else {
                controller.service(req, resp);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
