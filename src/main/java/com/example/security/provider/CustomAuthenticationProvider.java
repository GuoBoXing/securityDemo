package com.example.security.provider;

import com.example.security.details.CustomWebAuthenticationDetails;
import com.example.security.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CustomAuthenticationProvider
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/23 16:48
 * @Version 1.0
 **/
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取用户输入的用户名和密码
        String inputName = authentication.getName();
        String inputPassword = authentication.getCredentials().toString();

        CustomWebAuthenticationDetails authenticationDetails = (CustomWebAuthenticationDetails) authentication.getDetails();

        String verifyCode = authenticationDetails.getVerifyCode();

        if (!validateVerify(verifyCode)) {
            throw new DisabledException("验证码输入错误");
        }
        //userDetails为数据库中查询到的用户信息
        UserDetails userDetails = userDetailService.loadUserByUsername(inputName);

        // 如果是自定义AuthenticationProvider，需要手动密码校验
        if (!userDetails.getPassword().equals(inputPassword)) {
            throw new BadCredentialsException("密码错误");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,inputPassword,userDetails.getAuthorities());
    }

    private boolean validateVerify(String inputVerify) {
        // 获取当前线程绑定的request对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        // 获取用户输入的验证码
        String verifyCode = ((String) request.getSession().getAttribute("validateCode")).toLowerCase();
        inputVerify = inputVerify.toLowerCase();

        System.out.println("验证码："+verifyCode+",用户输入："+inputVerify);
        return inputVerify.equals(verifyCode);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
