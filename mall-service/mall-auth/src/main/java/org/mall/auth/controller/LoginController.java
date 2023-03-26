package org.mall.auth.controller;

import org.mall.auth.dto.UserRegisterDTO;
import org.mall.auth.service.LoginService;
import org.mall.common.constant.CommonConstant;
import org.mall.common.pojo.ResponseEntity;
import org.mall.member.dto.UserLoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author sxs
 * @since 2023/2/11
 */
@Controller
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/sms/sendCode")
    @ResponseBody
    public ResponseEntity sendCode(String phone) {
        loginService.sendCode(phone);
        return ResponseEntity.ok();
    }

    @GetMapping("/register.html")
    public String register(HttpSession session) {
        Object user = session.getAttribute(CommonConstant.LOGIN_SESSION_KEY);
        if (user == null) {
            return "register";
        }
        return "redirect:http://mall.org";
    }
    @GetMapping("/login.html")
    public String login(HttpSession session) {
        Object user = session.getAttribute(CommonConstant.LOGIN_SESSION_KEY);
        if (user == null) {
            return "login";
        }
        return "redirect:http://mall.org";
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity register(@Validated UserRegisterDTO userRegisterDTO) {
        loginService.register(userRegisterDTO);
        return ResponseEntity.ok();
    }
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity login(@Validated UserLoginDTO userLoginDTO, HttpSession httpSession) {
        loginService.login(userLoginDTO,httpSession);
        return ResponseEntity.ok();
    }
}
