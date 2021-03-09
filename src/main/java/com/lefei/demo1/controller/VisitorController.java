package com.lefei.demo1.controller;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lefei.demo1.exception.AbnormalAccessException;
import com.lefei.demo1.mapper.UserMapper;
import com.lefei.demo1.pojo.User;
import com.lefei.demo1.pojo.UserIncludeFile;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

/**
 * @author le
 * date:    2020/12/17
 * describe：
 */
@Controller
public class VisitorController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Logger logger;

    @RequestMapping("/logon")
    public String logon(String username, String password, String captcha, HttpSession session, Model model) throws Exception {
        if(username == null ||password == null ||captcha == null){
            throw new AbnormalAccessException("登录参数缺失");
        }
        if (!captcha.equals(session.getAttribute("captcha"))) {
            session.setAttribute("msg", "验证码错误");
            return "redirect:index";
        }
        User user = userMapper.logon(username, password);
        if (user == null) {
            session.setAttribute("msg", "用户名或密码错误");
            return "redirect:index";
        }
        //        设置session
        session.setAttribute("username", user.getUsername());
        session.setAttribute("password", user.getPassword());
        session.setAttribute("head", "img/upload/head/" + user.getHead_pic());
        return "redirect:listProduct";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:index";
    }


    @RequestMapping(value = {"/index"})
    public String index() {
        return "index";
    }

}
