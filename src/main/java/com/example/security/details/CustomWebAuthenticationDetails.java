package com.example.security.details;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CustomWebAuthenticationDetails
 * @Description 获取用户登陆的额外信息
 * @Author G-B-X
 * @Date 2019/7/23 16:15
 * @Version 1.0
 **/
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = -7619245006259525615L;
    private final String verifyCode;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        // verifyCode为验证码的name
        verifyCode = request.getParameter("verifyCode");
    }

    public String getVerifyCode() {
        return this.verifyCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(";VerifyCode：").append(this.getVerifyCode());
        return sb.toString();
    }
}
