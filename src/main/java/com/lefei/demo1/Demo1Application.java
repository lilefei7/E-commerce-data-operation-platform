package com.lefei.demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;

import javax.servlet.DispatcherType;
import javax.servlet.MultipartConfigElement;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication()
@MapperScan(basePackages = {"com.lefei.demo1.mapper"})

//关闭所有过滤器
//@ServletComponentScan
public class Demo1Application {
//    用  String path = ResourceUtils.getFile("classpath:static").getPath();
//    获得路径

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("C:\\Users\\le\\Desktop\\SCD\\src\\main\\resources\\static");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {

        SpringApplication.run(Demo1Application.class, args);
    }

}
