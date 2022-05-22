package util;


import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class HttpResponse {
    DataOutputStream dos;
    Map<String, String> headers;

    int statusCode;
    String statusMessage;

    public HttpResponse(OutputStream os) {
        this.dos = new DataOutputStream(os);
        headers = new HashMap<>();
    }

    public void addHeader(String field, String value) {
        headers.put(field, value);
    }

    public String getHeader(String field) {
        return headers.get(field);
    }

    private byte[] makeBody(String url) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        return body;
    }

    public void response200Header(int contentOfLength) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            processHeaders();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void processHeaders() {
        try {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody(byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forward(String url) {
        try {
            byte[] body = makeBody(url);

            if (url.endsWith(".css") || url.endsWith(".ico")) {
                log.debug("URL: {}", url);
                addHeader("Content-Type", "text/css");
            } else if (url.endsWith(".js")){
                addHeader("Content-Type", "application/javascript");
            } else {
                addHeader("Content-Type", "text/html;charset=utf-8");
            }
            addHeader("Content-Length", body.length + "");

            response200Header(body.length);
            responseBody(body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forwardBody(byte[] body) {
        addHeader("Content-Type", "text/html;charset=utf-8");
        addHeader("Content-Length", body.length + "");
        response200Header(body.length);
        responseBody(body);
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            processHeaders();
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
