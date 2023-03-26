package org.mall.seckill.interceptor;

import org.mall.common.constant.CommonConstant;
import org.mall.member.entity.Member;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sxs
 * @since 2023/3/10
 */
public class SeckillInterceptor implements HandlerInterceptor {
    public static ThreadLocal<Member> threadLocal = new ThreadLocal<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Member member = (Member) request.getSession().getAttribute(CommonConstant.LOGIN_SESSION_KEY);
        if (member != null) {
            threadLocal.set(member);
            return true;
        }
        boolean match = new AntPathMatcher().match("/feign/seckill/**", request.getRequestURI()) && !request.getHeader("Host").equals("seckill.mall.org");
        boolean match1= new AntPathMatcher().match("/seckill/skus", request.getRequestURI());
        if (match || match1) {
            return true;
        }
        request.getSession().setAttribute("msg","请先登录");
        response.sendRedirect("http://auth.mall.org/login.html");
        return false;
    }
}
