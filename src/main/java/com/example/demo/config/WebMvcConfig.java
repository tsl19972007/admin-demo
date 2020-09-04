package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author ：tsl
 * @date ：Created in 2020/8/22 21:05
 * @description：
 */

@Configuration
public class WebMvcConfig {
    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //次处时区与数据库的设置一致即可(或使用"Asia/Shanghai)
        objectMapper.setTimeZone(TimeZone.getDefault());
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        return mappingJackson2HttpMessageConverter;
    }
}
