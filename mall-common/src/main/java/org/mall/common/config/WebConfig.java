package org.mall.common.config;

import org.mall.common.util.JsonUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author sxs
 * @since 2023/1/13
 */
@ConditionalOnClass(value = {WebMvcConfigurer.class,HttpMessageConverter.class})
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                converters.add(i, new MappingJackson2HttpMessageConverter(JsonUtil.OBJECT_MAPPER));
                break;
            }
        }
    }


}
