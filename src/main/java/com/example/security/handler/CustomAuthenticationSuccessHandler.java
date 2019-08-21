//package com.example.security.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @ClassName CustomAuthenticationSuccessHandler
// * @Description 用户认证成功后处理的逻辑
// * @Author G-B-X
// * @Date 2019/7/24 16:11
// * @Version 1.0
// **/
//@Slf4j
//@Component
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//    /**
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param authentication 认证成功后用户的认证信息
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        log.info("登陆成功，{}",authentication);
//        httpServletResponse.sendRedirect("/");
//    }
//}
