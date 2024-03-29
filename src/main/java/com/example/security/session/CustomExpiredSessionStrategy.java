package com.example.security.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CustomExpiredSessionStrategy
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/25 10:54
 * @Version 1.0
 **/
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private ObjectMapper mapper = new ObjectMapper();

//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    // 处理相关逻辑
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>(16);
        map.put("code",0);
        map.put("msg","已经另一台机器登录，您被迫下线。" + event.getSessionInformation().getLastRequest());
        // Map ==》 JSON
        String json = mapper.writeValueAsString(map);
        event.getResponse().setContentType("application/json;charset=UTF-8");
        event.getResponse().getWriter().write(json);

        // 如果是跳转html页面，url代表跳转的地址
//        redirectStrategy.sendRedirect(event.getRequest(),event.getResponse(),"url");
    }
}
