package com.example.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName CustomLogoutSuccessHandler
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/29 15:12
 * @Version 1.0
 **/
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
      String username = ((User)authentication.getPrincipal()).getUsername();
      log.info("退出成功，用户名：{}",username);

      // 重定向到登录页
        httpServletResponse.sendRedirect("/login");
    }
}