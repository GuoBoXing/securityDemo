//package com.example.security.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @ClassName CustomAuthenticationFailureHandler
// * @Description 用户认证失败后处理的逻辑
// * @Author G-B-X
// * @Date 2019/7/24 16:16
// * @Version 1.0
// **/
//@Slf4j
//@Component
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    /**
//     *
//     * @param httpServletRequest
//     * @param httpServletResponse
//     * @param e 认证失败的异常
//     * @throws IOException
//     * @throws ServletException
//     */
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        log.info("登陆失败");
//        httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        httpServletResponse.setContentType("application/json;charset=UTF-8");
//        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(e.getMessage()));
//    }
//}
