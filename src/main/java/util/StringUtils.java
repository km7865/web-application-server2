package util;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class StringUtils {
    public String findURL(String[] tokens) {
        String url = "";
        for (String token : tokens) {
            Matcher m = Pattern.compile("(.*)\\.html").matcher(token);
            if (m.find()) {
                log.debug("Token found!: {}", token);
                url = token;
                break;
            }
        }
        return url;
    }

}
