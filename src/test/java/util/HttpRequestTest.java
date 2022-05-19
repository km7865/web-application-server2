package util;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void GET_요청() throws IOException {
        InputStream in = new FileInputStream(testDirectory + "Http_GET.txt");
        HttpRequest request = new HttpRequest(in);

        assertThat("GET").isEqualTo(request.getMethod());
        assertThat("/user/create").isEqualTo(request.getUrl());
        assertThat("keep-alive").isEqualTo(request.getHeader("Connection"));
        assertThat("dongwon").isEqualTo(request.getParameter("userId"));
    }

    @Test
    public void POST_요청() throws IOException {
        InputStream in = new FileInputStream(testDirectory + "Http_POST.txt");
        HttpRequest request = new HttpRequest(in);

        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getUrl()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("javajigi");
        assertThat(request.getParameter("name")).isEqualTo("Dongwon");
        assertThat(request.getCookie("logined")).isEqualTo("false");
    }
}
