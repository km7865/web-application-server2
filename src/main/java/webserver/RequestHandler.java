package webserver;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.StringUtils;

@Slf4j
public class RequestHandler extends Thread {

    private static final StringUtils su = new StringUtils();

    private Socket connection;

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void run() {
        log.debug("New Client Connected! Connected IP: {}, Port: {}"
                , connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO
            // HTTP 요청 헤더 읽어서 url 분리하기
            // webapp 디렉토리에서 html파일을 body에 저장 후 응답하기
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line, url = "";
            while (!"".equals(line = br.readLine()) && line != null) {
                log.debug("Client's Request: {}", line);
                url = su.findURL(line.split("/|\\s"));
                if (url != "") break;
            }
            byte[] body = makeBody(url);
            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private byte[] makeBody(String url) throws IOException {
        if (url == "") return "Hello World!".getBytes();
        return Files.readAllBytes(new File("./webapp/" + url).toPath());
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
