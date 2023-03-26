package org.mall.auth.service;

import org.mall.auth.dto.UserRegisterDTO;
import org.mall.member.dto.UserLoginDTO;

import javax.servlet.http.HttpSession;

/**
 * @author sxs
 * @since 2023/2/11
 */
public interface LoginService {

    void sendCode(String phone);

    /**
     * 注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    void login(UserLoginDTO userLoginDTO, HttpSession httpSession);
}
