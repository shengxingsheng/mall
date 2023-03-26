package org.mall.seckill.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.mall.common.constant.ErrorCode;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.JsonUtil;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sxs
 * @since 2023/3/12
 */
@Configuration
public class SentinelConfig implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {

        response.setContentType("application/json;charset=UTF-8");
        ResponseEntity error = ResponseEntity.error(ErrorCode.SYSTEM_CURRENT_LIMITING, "请求过于频繁");
        response.getWriter().write(JsonUtil.classToJson(error));
    }
}
