package org.mall.cart.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.mall.cart.dto.UserInfoDTO;
import org.mall.common.constant.CommonConstant;
import org.mall.common.util.CookieUtil;
import org.mall.member.entity.Member;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

/**
 * @author sxs
 * @since 2023/2/16
 */
public class CartInterceptor implements HandlerInterceptor {
    public static ThreadLocal<UserInfoDTO> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<Object> memberOptional = Optional.ofNullable(request.getSession().getAttribute(CommonConstant.LOGIN_SESSION_KEY));
        UserInfoDTO userInfo = new UserInfoDTO();
        memberOptional.ifPresent(o -> userInfo.setUserId(((Member) o).getId()));
        String userKey = CookieUtil.getCookieValue(request.getCookies(), CommonConstant.TEMP_USER_COOKIE_NAME);
        if (StringUtils.isBlank(userKey)) {
            userKey = UUID.randomUUID().toString();
        }
        userInfo.setUserKey(userKey);
        threadLocal.set(userInfo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoDTO userInfo = CartInterceptor.threadLocal.get();
        if (userInfo.getUserId() == null) {
            Cookie cookie = new Cookie(CommonConstant.TEMP_USER_COOKIE_NAME, userInfo.getUserKey());
            cookie.setDomain("mall.org");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(CommonConstant.TEMP_USER_COOKIE_SURVIVE);
            response.addCookie(cookie);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
