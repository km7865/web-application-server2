package util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestLineTest {
    @Test
    public void return_request_line() {
        String line = "GET /index.html HTTP/1.1";

        RequestLine requestLine = new RequestLine(line);

        assertThat(requestLine.getPath()).isEqualTo("/index.html");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getParams()).isEmpty();
    }

    @Test
    public void return_params() {
        String line = "GET /user/create?userId=dongwon&password=password&name=dongwon&email=dongwon@naver.com HTTP/1.1";

        RequestLine requestLine = new RequestLine(line);

        assertThat(requestLine.getPath()).isEqualTo("/user/create");
        assertThat(requestLine.getMethod()).isEqualTo("GET");
        assertThat(requestLine.getParams().get("userId")).isEqualTo("dongwon");
        assertThat(requestLine.getParams().get("password")).isEqualTo("password");
        assertThat(requestLine.getParams().get("name")).isEqualTo("dongwon");
        assertThat(requestLine.getParams().get("email")).isEqualTo("dongwon@naver.com");
    }
}
