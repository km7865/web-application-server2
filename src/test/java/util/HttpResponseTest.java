package util;


import org.checkerframework.checker.signature.qual.DotSeparatedIdentifiersOrPrimitiveType;
import org.junit.jupiter.api.Test;

import java.io.*;

public class HttpResponseTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void forward() throws IOException {
        OutputStream os = new FileOutputStream(testDirectory + "Http_Forward.txt");
        HttpResponse resp = new HttpResponse(os);

        resp.forward("/index.html");
    }

    @Test
    public void forward_css() throws IOException {
        OutputStream os = new FileOutputStream(testDirectory + "Http_Forward_Css.txt");
        HttpResponse resp = new HttpResponse(os);

        resp.forward("/css/bootstrap.min.css");
    }

    @Test
    public void sendRedirect() throws IOException {
        OutputStream os = new FileOutputStream(testDirectory + "Http_Redirect.txt");
        HttpResponse resp = new HttpResponse(os);

        resp.sendRedirect("/index.html");
    }

    @Test
    public void sendRedirectWithCookie() throws IOException {
        OutputStream os = new FileOutputStream(testDirectory + "Http_Redirect_Cookie.txt");
        HttpResponse resp = new HttpResponse(os);

        resp.addHeader("Set-Cookie", "logined=true;");
        resp.sendRedirect("/index.html");
    }
}
