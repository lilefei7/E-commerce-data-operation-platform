package com.lefei.demo1.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author le
 * date:    2020/12/17
 * describe：
 */
@Component
@Configuration
public class LoggerrConfig {
//    这样配可以用，但是很慌。能不能用@Component实现随地的带一个logger的功能
    @Bean
    public Logger getLogger() {
        LoggerFactory.getLogger(getClass());
        return  LoggerFactory.getLogger(getClass());
    }
}