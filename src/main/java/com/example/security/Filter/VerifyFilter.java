package com.example.security.Filter;

import org.springframework.security.authentication.DisabledException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @ClassName VerifyFilter
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 18:49
 * @Version 1.0
 **/
public class VerifyFilter extends OncePerRequestFilter{

    private static final PathMatcher pathMathcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isProtecteUrl(request)) {
            String verifyCode = request.getParameter("verifyCode");
            if (!validateVerify(verifyCode)) {
                // 手动设置异常
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION",new DisabledException("验证码输入错误"));
                // 转发到错误的url
                request.getRequestDispatcher("/login/error").forward(request,response);
            } else {
                filterChain.doFilter(request,response);
            }
        } else {
            filterChain.doFilter(request,response);
        }
    }


    private boolean validateVerify(String inputVerify) {
        // 获取当前x线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        // 不区分大小写
        // 这个validateCode实在servlet中存入session的名字
        String validaateCode = ((String)request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();

        System.out.println("验证码：" + validaateCode +"用户输入：" + inputVerify);
        return validaateCode.equals(inputVerify);
    }

    private boolean isProtecteUrl(HttpServletRequest request) {
        return "POST".equals(request.getMethod()) && pathMathcher.match("/login",request.getServletPath());
    }
}
