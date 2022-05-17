package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilTest {
    private static StringUtils utils;

    @BeforeEach
    public void setup() {
        utils = new StringUtils();
    }

    @Test
    public void URL이_반환된다() {
        String expected = "index.html";
        String[] tokens = {"GET", "index.html", "HTTP", "1.1"};

        assertThat(utils.findURL(tokens)).isEqualTo(expected);
    }

    @Test
    public void 빈_문자열이_반환된다() {
        String expected = "";
        String[] tokens = {""};

        assertThat(utils.findURL(tokens)).isEqualTo(expected);
    }

}
