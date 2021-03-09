package com.lefei.demo1.controller;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lefei.demo1.mapper.UserMapper;
import com.lefei.demo1.pojo.User;
import com.lefei.demo1.pojo.UserIncludeFile;
import com.lefei.demo1.service.UserService;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    Logger logger;


    @RequestMapping("/addUser")
    public String addUser(UserIncludeFile userIncludeFile) throws Exception {
        userService.addUser(userIncludeFile);
        return "redirect:listUser";
    }

    //返回一个表单
    @RequestMapping("/editUser")
    public String editUser(int id, Model model, HttpSession session) throws Exception {
        userService.editUser(id, model, session);
        return "admin/editUser";
    }

    @RequestMapping("/updateUser")
    public String updateUser(UserIncludeFile userIncludeFile, HttpSession session) throws Exception {
        userService.updateUser(userIncludeFile, session);
        return "redirect:listUser";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(User user) throws Exception {
        userService.deleteUser(user);
        return "redirect:listUser";
    }

    @RequestMapping(value = {"/listUser"})
    public String listUser(Model m, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        userService.listUser(m, start, size);
        return "admin/listUser";
    }

    @RequestMapping(value = {"/exportUser"})
    public void export(HttpServletResponse response) throws IORuntimeException, IOException {
        userService.export(response);
    }
}
