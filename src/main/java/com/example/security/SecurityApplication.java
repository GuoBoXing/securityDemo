package com.example.security;

import com.example.security.servlet.VerifyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.servlet.ServletRegistration;

@EnableRedisHttpSession  // session共享
@SpringBootApplication
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	/**
	 * 注入验证码servlet
	 */
	@Bean
	public ServletRegistrationBean indexServletRegistration() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(new VerifyServlet());
		registrationBean.addUrlMappings("/getVerifyCode");
		return registrationBean;
	}
}
