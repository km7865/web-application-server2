package util;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void GET_JS_요청() throws IOException {
        InputStream in = new FileInputStream(testDirectory + "Http_GET_js.txt");
        HttpRequest request = new HttpRequest(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/js/bootstrap.min.js");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    public void GET_CSS_요청() throws IOException {
        InputStream in = new FileInputStream(testDirectory + "Http_GET_css.txt");
        HttpRequest request = new HttpRequest(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);
        assertThat(request.getPath()).isEqualTo("/css/bootstrap.min.css");
        assertThat(request.getHeader("Accept")).isEqualTo("text/css");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
    }

    @Test
    public void POST_요청() throws IOException {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        HttpRequest request = new HttpRequest(in);

        assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("name")).isEqualTo("Dongwon");
        assertThat(request.getCookie("logined")).isEqualTo("false");
    }
}
