package org.mall.member.config;

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

/**
 * @author sxs
 * @since 2023/3/5
 */
@Configuration
public class FeignConfig {

    @Bean
    public Decoder feignDecoder(ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        ObjectFactory<HttpMessageConverters> messageConverters = () -> {
            return new HttpMessageConverters(new MappingJackson2HttpMessageConverter(JsonUtil.OBJECT_MAPPER));
        };
        return new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters, customizers)));
    }
}
