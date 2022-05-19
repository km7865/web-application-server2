package util;


import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpResponse {
    DataOutputStream dos;
    Map<String, String> headers;

    int statusCode;
    String statusMessage;
    byte[] body;

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

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setBody(byte[] body) {
        this.body = body;
        addHeader("Content-Length", Integer.toString(body.length));
    }
    private void makeBody(String url) throws IOException {
        body = Files.readAllBytes(new File("./webapp" + url).toPath());
        addHeader("Content-Length", Integer.toString(body.length));
    }

    public void response200Header() {
        int lengthOfBodyContent = Integer.parseInt(getHeader("Content-Length"));
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void responseBody() {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void forward(String url) {
        try {
            if (url.endsWith(".css") || url.endsWith(".ico")) {
                log.debug("URL: {}", url);
                addHeader("Content-Type", "text/css");
            } else {
                addHeader("Content-Type", "text/html;charset=utf-8");
            }

            makeBody(url);
            int lengthOfBodyContent = Integer.parseInt(getHeader("Content-Length"));
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            dos.writeBytes("Content-Type: " + getHeader("Content-Type") + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            if (headers.containsKey("Set-Cookie")) {
                dos.writeBytes("Set-Cookie: " + getHeader("Set-Cookie") + "\r\n");
            }
            dos.writeBytes("\r\n");
            dos.write(body, 0, lengthOfBodyContent);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void sendRedirect(String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            if (headers.containsKey("Set-Cookie")) {
                dos.writeBytes("Set-Cookie: " + getHeader("Set-Cookie") + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
