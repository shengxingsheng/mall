package org.mall.order.listener;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.mall.order.config.AlipayTemplate;
import org.mall.order.service.OrderService;
import org.mall.order.vo.PayAsyncVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author sxs
 * @since 2023/3/5
 */
@Controller
public class OrderPayedListener {

    private final OrderService orderService;
    private final AlipayTemplate alipayTemplate;

    public OrderPayedListener(OrderService orderService, AlipayTemplate alipayTemplate) {
        this.orderService = orderService;
        this.alipayTemplate = alipayTemplate;
    }

    @ResponseBody
    @PostMapping("/aliPay/notify")
    public String aliPayNotify(PayAsyncVo payAsyncVo, HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException, JsonProcessingException {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type());
        if (signVerified) {
            System.out.println("签名验证成功...");
            orderService.aliPayNotify(payAsyncVo);
            return "success";
        } else {
            System.out.println("签名验证失败...");
            return "fail";
        }
    }

}
