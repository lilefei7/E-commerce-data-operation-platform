package com.lefei.demo1.service.implement;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.google.code.kaptcha.Producer;
import com.lefei.demo1.controller.CaptchaController;
import com.lefei.demo1.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author le
 * date:    2020/12/23
 * describe：
 */
@Service
public class CaptchaServiceImp implements CaptchaService {

    @Autowired
    private Producer producer;

    private static Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Override
    public void generateCodeAndReturn(HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", 0);// 禁止server端缓存
        // 设置标准的 HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 设置IE扩展 HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");// 设置标准 HTTP/1.0 不缓存图片
        response.setContentType("image/jpeg");// 返回一个 jpeg 图片，默认是text/html(输出文档的MIMI类型)
        String captcha = producer.createText();// 为图片创建文本

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        servletRequestAttributes.setAttribute("captcha", captcha, RequestAttributes.SCOPE_SESSION);
        ServletOutputStream outputStream = response.getOutputStream();
        BufferedImage bi = producer.createImage(captcha); // 创建带有文本的图片
        // 图片数据输出
        ImageIO.write(bi, "jpg", outputStream);
        try {
            outputStream.flush();
        } finally {
            outputStream.close();
        }


//        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        CaptchaUtil.
//        LineCaptcha lineCaptcha =
//                CaptchaUtil.createLineCaptcha(200, 100,1,1);
//        String captcha = lineCaptcha.getCode();
//        servletRequestAttributes.setAttribute("captcha",captcha,RequestAttributes.SCOPE_SESSION);
//        ServletOutputStream outputStream = response.getOutputStream();
//        lineCaptcha.write(outputStream);
//        outputStream.flush();
//        outputStream.close();
        //Servlet的OutputStream记得自行关闭哦！

    }
}
