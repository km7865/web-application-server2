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
    private RequestLine requestLine;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> cookies = new HashMap<>();

    public HttpRequest(InputStream is) {
        try {
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = br.readLine();
            if (line == null) {
                return;
            }

            requestLine = new RequestLine(line);

            String[] tokens;
            while (!line.equals("")) {
                line = br.readLine();
                log.debug("Request header: {}", line);
                tokens = line.split(": ");
                if (tokens.length == 2) {
                    headers.put(tokens[0], tokens[1]);
                }
            }
            if (requestLine.getMethod().isPost()) {
                String resource = IOUtils.readData(br, Integer.parseInt(getHeader("Content-Length")));
                params = Arrays.stream(resource.split("&")).map(t -> getKeyValue(t, "="))
                        .filter(p -> p != null).collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
            } else {
                params = requestLine.getParams();
            }
            cookies = HttpRequestUtils.parseCookies(getHeader("Cookie"));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    public String getHeader(String field) {
        return headers.get(field);
    }

    public String getParameter(String param) {
        return params.get(param);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public String getPath() {
        return requestLine.getPath();
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }
}
