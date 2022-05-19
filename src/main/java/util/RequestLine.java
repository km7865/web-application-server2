package util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestLine {
    private String path;
    private HttpMethod method;
    private Map<String, String> params = new HashMap<String, String>();

    public RequestLine(String line) {
        log.debug("Request Line: {}", line);
        String[] tokens = line.split(" ");
        method = HttpMethod.valueOf(tokens[0]);
        if (method == HttpMethod.POST) {
            path = tokens[1];
            return;
        }

        int idx = tokens[1].indexOf("?");
        if (idx == -1) {
            path = tokens[1];
        } else {
            path = tokens[1].substring(0, idx);
            params = HttpRequestUtils.parseQueryString(tokens[1].substring(idx + 1));
        }
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
