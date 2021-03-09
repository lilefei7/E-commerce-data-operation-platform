package com.lefei.demo1.controller;

import com.lefei.demo1.service.CaptchaService;
import com.lefei.demo1.service.implement.CaptchaServiceImp;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author le
 * date:    2020/12/21
 * describe：
 */
@Controller
public class CaptchaController {

    @Autowired
    CaptchaService captchaService;
    @Autowired
    Logger logger;
    @Autowired
    WebApplicationContext webApplicationContext;
    @RequestMapping(value = "/captcha.jpg")
    @ApiOperation("查询用户分页列表")
    public void kaptcha(HttpServletResponse response) throws IOException {
        captchaService.generateCodeAndReturn(response);
    }
}
