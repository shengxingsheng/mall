package org.mall.member.interceptor;

import org.mall.common.constant.CommonConstant;
import org.mall.member.entity.Member;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sxs
 * @since 2023/3/5
 */

public class MemberInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Member> loginUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Member member = (Member)request.getSession().getAttribute(CommonConstant.LOGIN_SESSION_KEY);
        if (member == null) {
            boolean match = new AntPathMatcher().match("/feign/member/**", request.getRequestURI());
            if (match) {
                return true;
            }
            request.getSession().setAttribute("msg","请先登录");
            response.sendRedirect("http://auth.mall.org/login.html");
            return false;
        }
        loginUser.set(member);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
