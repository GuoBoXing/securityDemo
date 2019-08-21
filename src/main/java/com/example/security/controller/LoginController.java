package com.example.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author G-B-X
 * @Date 2019/7/22 14:34
 * @Version 1.0
 **/

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @RequestMapping("/")
    private String showHome() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("当前登陆用户："+ name);
        return "home.html";
    }

    @RequestMapping("/me")
    @ResponseBody
    private Object me(Authentication authentication) {
        return authentication;
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login.html";
    }
    @RequestMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String printAdmin() {
        return "当你看见这句话的时候，说明你有ROLE_ADMIN的角色";
    }

//    @RequestMapping("/admin")
//    @ResponseBody
//    @PreAuthorize("hasPermission('/admin','r')")
//    public String printAdminR() {
//        return "当你看见这句话的时候，说明你访问/admin路径具有r权限";
//    }
//
//    @RequestMapping("/admin/c")
//    @ResponseBody
//    @PreAuthorize("hasPermission('/admin','c')")
//    public String printAdminC() {
//        return "当你看见这句话的时候，说明你访问/admin路径具有c权限";
//    }


    @RequestMapping("/user")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public String printUser() {
        return "当你看见这句话的时候，说明你有ROLE_USER的角色";
    }


    @RequestMapping("/login/invalid")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String invalid() {
        return "sessionOverdue.html";
    }


    /**
     * 踢出用户的controller
     * @param username
     * @return
     */
    @GetMapping("/kick")
    @ResponseBody
    public String removeUserSessionByUsername(@RequestParam String username) {
        int count =  0;
        // 获取session中所有的用户信息
        List<Object> users = sessionRegistry.getAllPrincipals();
        for (Object principal:users) {
            if (principal instanceof User) {
                // 获取到用户信息的主体
                String principalName = ((User) principal).getUsername();
                if (principalName.equals(username)) {
                    // 参数二：是否包含过期的session
                    List<SessionInformation> sessionInfo = sessionRegistry.getAllSessions(principal,false); // 获取该主体上所有的session
                    if (sessionInfo != null && sessionInfo.size() > 0) {
                        for (SessionInformation sessionInformat:sessionInfo) {
                            // 使session过期
                            sessionInformat.expireNow();
                            count++;
                        }
                    }
                }
            }
        }
        return "操作成功，共清理session"+ count +"个";
    }

    @RequestMapping("/sms/code")
    @ResponseBody
    public void sms(String mobile, HttpSession session) {
        int code = (int) Math.ceil(Math.random() * 9000 + 1000);
        Map<String,Object> map = new HashMap<>(16);
        map.put("mobile",mobile);
        map.put("code",code);

        session.setAttribute("smsCode",map);
        log.info("{}：为{}设置短信验证码：{}",session.getId(),mobile,code);
    }
}
