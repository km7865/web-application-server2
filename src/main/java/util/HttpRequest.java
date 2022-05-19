package util;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static util.HttpRequestUtils.getKeyValue;

/*
InputStream 을 생성자로 받아 Http Request 를 받아서 메소드, URL, 헤더, 본문 으로 분리하기
헤더는 Map<String, String> 타입이며, getHeader("field") 를 통해 value 를 얻을 수 있도록 구현
GET, POST 메소드에 따라 전달되는 인자를 getParameter("param")를 통해 value 를 얻을 수 있도록 구현
 */

@Slf4j
public class HttpRequest {
    private BufferedReader br;
    private Map<String, String> headers;
    private Map<String, String> params;
    private Map<String, String> cookies;
    private String url;
    private String method;
    private byte[] body;

    public HttpRequest(InputStream is) throws IOException {
        headers = new HashMap<>();
        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String line = br.readLine();
        if (line == null) {
            return;
        }
        log.debug("Request Line: {}", line);
        String[] tokens = line.split(" ");
        method = tokens[0];
        url = tokens[1];

        while (!line.equals("")) {
            line = br.readLine();
            log.debug("Request header: {}", line);
            tokens = line.split(": ");
            if(tokens.length == 2) {
                headers.put(tokens[0], tokens[1]);
            }
        }

        if (headers.get("Content-Length") != null) {
             int contentLength = Integer.parseInt(getHeader("Content-Length"));
             char[] tmp = new char[contentLength];
             br.read(tmp, 0, contentLength);
             body = String.copyValueOf(tmp).getBytes();
             log.debug("content: {}", String.copyValueOf(tmp));
        } else {
            headers.put("Content-Length", "0");
        }

        cookies = HttpRequestUtils.parseCookies(getHeader("Cookie"));

        String resource="";
        if (method.equals("GET")) {
            tokens = url.split("\\?");
            if (tokens.length == 2) {
                url = tokens[0];
                resource = tokens[1];
            }
        } else if (method.equals("POST")) {
            resource = new String(body);
        }
        params = Arrays.stream(resource.split("&")).map(t -> getKeyValue(t, "="))
                .filter(p -> p != null).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
    }

    public boolean containsHeader(String field) {
        return headers.containsKey(field);
    }

    public String getHeader(String field) {
        String value = headers.get(field);
        return value == null ? "" : value;
    }

    public String getParameter(String param) {
        return params.get(param);
    }

    public String getCookie(String name) {

        return cookies.get(name);
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public byte[] getBody() {
        return body;
    }
}
