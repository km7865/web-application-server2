package controller;

import util.HttpMethod;
import util.HttpRequest;
import util.HttpResponse;

public abstract class AbstractController implements Controller {
    @Override
    public void service(HttpRequest req, HttpResponse resp) {
        if (req.getMethod().isPost()) {
            doPost(req, resp);
        } else {
            doGet(req, resp);
        }
    }

    protected void doGet(HttpRequest req, HttpResponse resp) {

    }
    protected void doPost(HttpRequest req, HttpResponse resp) {

    }
}
