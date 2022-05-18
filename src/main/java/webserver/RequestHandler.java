package webserver;

import db.DataBase;
import lombok.extern.slf4j.Slf4j;
import model.User;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.IOUtils;
import util.StringUtils;
import util.HttpRequestUtils;

@Slf4j
public class RequestHandler extends Thread {

    private Socket connection;

    private static final List<User> users = new ArrayList<>();

    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        //log.debug("New Client Connected! Connected IP: {}, Port: {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO
            // HTTP 요청 헤더 읽어서 url 분리하기
            // webapp 디렉토리에서 html파일을 body에 저장 후 응답하기
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            log.debug("Request line: {}", line);
            if(line == null) {
                return;
            }
            String url = line.split(" ")[1];
            int contentLength = 0;
            while (!line.equals("")) {
                line = br.readLine();
                if (line.startsWith("Content-Length")) {
                    contentLength = getContentLength(line);
                }
                log.debug(line);
            }

            byte[] body;
            if (url.startsWith("/user/create")) {
                String data = IOUtils.readData(br, contentLength);
                Map<String, String> map = HttpRequestUtils.parseQueryString(data);
                User user = new User(map.get("userId"), map.get("password")
                        , map.get("name"), map.get("email"));
                DataBase.addUser(user);

                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "/index.html");
            } else if (url.equals("/user/login")) {
                String data = IOUtils.readData(br, contentLength);
                Map<String, String> params = HttpRequestUtils.parseQueryString(data);

                // 로그인
//                List<User> passedUser = users.stream().filter(user -> user.getUserId().equals(loginUser.get("userId")))
//                        .filter(user -> user.getPassword().equals(loginUser.get("password")))
//                        .collect(Collectors.toList());
//                DataOutputStream dos = new DataOutputStream(out);
//                if (passedUser.size() > 0) {
//                    response302HeaderWithCookie(dos, "/index.html", true);
//                    log.debug("-----------Login success!-----------");
//                } else {
//                    response302HeaderWithCookie(dos, "/user/login_failed.html", false);
//                    log.debug("-----------Login failed!-----------");
//                }
                User user = DataBase.findUserById(params.get("userId"));
                if (user == null) {
                    responseResource(out, "/user/login_failed.html");
                    return;
                }

                if (user.getPassword().equals(params.get("password"))) {
                    response302HeaderWithCookie(out, "/index.html");
                } else {
                    responseResource(out, "/user/login_failed.html");
                }
            } else {
                responseResource(out, url);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private int getContentLength(String line) {
        return Integer.parseInt(line.substring(line.indexOf(":") + 2));
    }

    private byte[] makeBody(String url) throws IOException {
        return Files.readAllBytes(new File("./webapp" + url).toPath());
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

    private void responseResource(OutputStream out, String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = makeBody(url);
        response200Header(dos, body.length);
        responseBody(dos, body);
    }

    private void response302Header(DataOutputStream dos, String redirectUrl) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302HeaderWithCookie(OutputStream out, String redirectUrl) {
        try {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Location: " + redirectUrl + "\r\n");
            dos.writeBytes("Set-Cookie: logined=true\r\n");
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
