package org.mall.ware.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.order.vo.PayVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayTemplate {
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    private String app_id = "";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "rMRKQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String notify_url = "http://9afhj4.natappfree.cc/aliPay/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String return_url = "http://member.mall.org/memberOrder.html?page=1&limit=10";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";
    private String time_out = "1m";
    // 支付宝网关
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    //    private String log_path = "C:\\";
    public String pay(PayVo vo) throws AlipayApiException {
        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + time_out + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest);
        if (response.isSuccess()) {
            log.info("支付调用成功,out_trade_no:{},total_amount:{}", out_trade_no, total_amount);
            return response.getBody();
        } else {
            log.error("支付调用失败,{}", response.getBody());
            return "<script>location.href='" + return_url + "'</script>";
        }


    }

    /**
     * 根据用户订单号查询支付宝订单
     * @param orderSn
     * @return
     */
    public String query(String orderSn,String tradeNo) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);
        //设置请求参数
        AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

        //商户订单号，商户网站订单系统中唯一订单号
        String out_trade_no = orderSn;
        //支付宝交易号
        String trade_no = tradeNo;
        if (StringUtils.isNotBlank(tradeNo)) {
            alipayRequest.setBizContent("{\"trade_no\":\""+ trade_no +"\"}");
        }else {
            alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"}");
        }
        //请二选一设置
        //请求
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(alipayRequest);
            if (response.isSuccess()) {
                log.error("查询成功");
                return response.getBody();
            }else {
                log.error("查询错误");
                return response.getBody();
            }
        } catch (AlipayApiException e) {
            log.info(e.getErrMsg());
            return "";
        }
    }
}