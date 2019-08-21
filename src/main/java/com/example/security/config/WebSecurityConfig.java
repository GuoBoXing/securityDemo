package com.example.security.config;

import com.example.security.Filter.VerifyFilter;
import com.example.security.evaluator.CustomPermissionEvaluator;
//import com.example.security.handler.CustomAuthenticationFailureHandler;
//import com.example.security.handler.CustomAuthenticationSuccessHandler;
import com.example.security.handler.CustomLogoutSuccessHandler;
import com.example.security.provider.CustomAuthenticationProvider;
import com.example.security.service.CustomUserDetailService;
import com.example.security.session.CustomExpiredSessionStrategy;
import com.example.security.sms.SmsCodeAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 15:14
 * @Version 1.0
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailService userDetailService;

    // 存储token
    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

//    @Autowired
//    private CustomAuthenticationSuccessHandler successHandler;
//
//    @Autowired
//    private CustomAuthenticationFailureHandler failureHandler;

    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(new PasswordEncoder() {  // 使用自带的验证
//            @Override
//            public String encode(CharSequence charSequence) {
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                return s.equals(charSequence.toString());
//            }
//        });
//        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(customAuthenticationProvider); //自定义，指定使用
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.apply(smsCodeAuthenticationSecurityConfig).and().authorizeRequests()
                // 如果允许匿名的url，填在下面
//                .antMatchers().permitAll()
                .antMatchers("/sms/**").permitAll() //短信验证登录放行
                .antMatchers("/getVerifyCode","/login","/login/logout","/login/invalid").permitAll()
                .anyRequest().authenticated()
                .and()
                // 设置登录页
                .formLogin().loginPage("/login")
                //设置登陆成功页
//                .successHandler(successHandler)
//                .failureHandler(failureHandler)
//                .defaultSuccessUrl("/").permitAll()
                // 登陆失败的url
//                .failureUrl("/login/error")
                // 自定义登陆用户名和密码参数，默认为username和password
//                .usernameParameter("username")
//                .passwordParameter("password")
                //指定authenticationDetailsSource
                .authenticationDetailsSource(authenticationDetailsSource)
                .and()
                // Spring Security 对于用户名/密码登录方式是通过 UsernamePasswordAuthenticationFilter 处理的，我们在它之前执行验证码过滤器即可。
//                .addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)  因为自定义的，所以需要自己实现校验
                .logout().logoutUrl("/signout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler)
                //自动登陆
//                .and().rememberMe();
                .and().rememberMe()
                .tokenRepository(persistentTokenRepository())
                // 有效时间，单位s
                .tokenValiditySeconds(60)
                .userDetailsService(userDetailService)
                .and()
                .sessionManagement().invalidSessionUrl("/login/invalid")
//                // 指定最大登录数
                .maximumSessions(1)
//                //当达到最大值时是否保留已经登陆的用户，true，新用户无法登录，false为
                .maxSessionsPreventsLogin(false)
//                // 当达到最大值时，旧用户被踢出的操作
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
                .sessionRegistry(sessionRegistry());
        // 关闭CSRF跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，k可以对静态资源放行
        web.ignoring().antMatchers("/css/**","/js/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);

        // 如果tokenb表不存在，使用下面语句可以初始化该表，若存在，请注释掉这条语句，否则会报错
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 注入自定义permissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new CustomPermissionEvaluator());
        return handler;
    }
    // 提出用户
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
