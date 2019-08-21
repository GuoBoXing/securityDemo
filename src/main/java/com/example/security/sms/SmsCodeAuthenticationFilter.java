package com.example.security.sms;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName SmsAuthenticationFilter
 * @Description 短信登录的过滤器实现，，模仿UsernamePasswordAuthenticationFilter实现
 * @Author G-B-X
 * @Date 2019/8/19 12:01
 * @Version 1.0
 **/
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * form表单中手机号码的字段name
     */
    private static final String SPRING_SECURITY_FROM_MOBILE_KEY = "mobile";

    private String mobileParameter = SPRING_SECURITY_FROM_MOBILE_KEY;

    /**
     * 是否仅post方式
     */
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        // 短信登录的请求post方式的 /sms/login
        super(new AntPathRequestMatcher("/sms/login","POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authenticatoin method not supported:" + httpServletRequest.getMethod());
        }

        String mobile = obtainMobile(httpServletRequest);

        if (mobile == null) {
            mobile = "";
        }
        mobile = mobile.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetials(httpServletRequest,authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetials(HttpServletRequest httpServletRequest, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));
    }

    private String obtainMobile(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getParameter(mobileParameter);
    }

    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter,"Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}
