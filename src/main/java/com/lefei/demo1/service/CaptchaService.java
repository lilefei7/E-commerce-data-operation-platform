package com.lefei.demo1.service;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author le
 * date:    2020/12/23
 * describe：
 */
public interface CaptchaService {
    //生成验证码并且返回
    void generateCodeAndReturn(HttpServletResponse response) throws IOException;

}
