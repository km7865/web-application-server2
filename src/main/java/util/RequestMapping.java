package util;

import controller.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


// 웹 애플리케이션에서 서비스하는 모든 url 과 Controller 를 관리하며,
// 요청 url 에 해당하는 Controller 를 반환하는 역할의 클래스
@Slf4j
public class RequestMapping {
    private static final Map<String, Controller> controllers = new HashMap<String, Controller>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/login", new LoginController());
        controllers.put("/user/list", new ListUserController());
    }

    public static Controller getController(String url) {
        return controllers.get(url);
    }
}
