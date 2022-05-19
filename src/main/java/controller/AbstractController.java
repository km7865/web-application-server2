package controller;

import util.HttpMethod;
import util.HttpRequest;
import util.HttpResponse;

public class AbstractController implements Controller {
    @Override
    public void service(HttpRequest req, HttpResponse resp) {
        if (req.getMethod() == HttpMethod.GET) {
            doGet(req, resp);
        } else if (req.getMethod() == HttpMethod.POST) {
            doPost(req, resp);
        }
    }

    void doGet(HttpRequest req, HttpResponse resp) {
        resp.forward(req.getPath());
    }
    void doPost(HttpRequest req, HttpResponse resp) {
        resp.sendRedirect("/index.html");
    }
}
