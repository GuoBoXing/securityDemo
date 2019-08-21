package com.example.security.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * @ClassName SmsAuthenticationToken
 * @Description 短信登录AuthenticationToken,模仿UsernamepasswordAuthenticationToken实现
 * @Author G-B-X
 * @Date 2019/8/19 11:45
 * @Version 1.0
 **/
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {


    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;


    /**
     * 在UsernamePasswordAuthenticationToken中，该字段代表用户名
     * 在这里就代表登录的验证码
     */
    private final Object principal;

    /**
     * 构建一个没有鉴权的token
     * @return
     */
    public SmsCodeAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    /**
     * 构建一个拥有鉴权的SmsCodeAuthticationToken
     * @return
     */
    public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("cannot set this token trused - use constructor which takes a GrantedAuthtity list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
