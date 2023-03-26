package org.mall.product.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import org.mall.common.util.JsonUtil;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * feing 配置 拦截器 解析器
 * @author sxs
 * @since 2023/3/9
 */
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (requestAttributes != null) {
                    template.header("Cookie", requestAttributes.getRequest().getHeader("Cookie"));
                }
            }
        };
    }
    @Bean
    public Decoder feignDecoder(ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        ObjectFactory<HttpMessageConverters> messageConverters = () -> new HttpMessageConverters(new MappingJackson2HttpMessageConverter(JsonUtil.OBJECT_MAPPER));
        return new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters,customizers)));
    }
}
