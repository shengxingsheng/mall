package org.mall.order.config;

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
 * @author sxs
 * @since 2023/2/19
 */
@Configuration
public class FeignConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    //远程调用带cookie
                    template.header("Cookie",attributes.getRequest().getHeader("Cookie"));
                }
            }
        };
    }
    @Bean
    public Decoder feignDecoder(ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        ObjectFactory<HttpMessageConverters> messageConverters = () -> {
            return new HttpMessageConverters(new MappingJackson2HttpMessageConverter(JsonUtil.OBJECT_MAPPER));
        };
        return new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters, customizers)));
    }

}
