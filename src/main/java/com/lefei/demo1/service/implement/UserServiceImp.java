package com.lefei.demo1.service.implement;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lefei.demo1.mapper.UserMapper;
import com.lefei.demo1.pojo.User;
import com.lefei.demo1.pojo.UserIncludeFile;
import com.lefei.demo1.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;

/**
 * @author le
 * date:    2020/12/29
 * describe：
 */
@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Logger logger;
    @Value("${myPath}")
    String path;
    @Override
    public void addUser(UserIncludeFile userIncludeFile) throws IOException {
        String realname = userIncludeFile.getHead_pic().getOriginalFilename();
//        获取图片后缀
        String suffix = realname.substring(realname.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + suffix;
//        String path = ResourceUtils.getFile("classpath:static").getPath();
        File file = new File(path + "/img/upload/head/" + saveName);
        MultipartFile head_pic = userIncludeFile.getHead_pic();
        FileCopyUtils.copy(head_pic.getInputStream(), Files.newOutputStream(file.toPath()));
//        head_pic.transferTo(file);
        User user = new User(userIncludeFile);
//        将图片的路径存入user对象中，再将user存入数据库
        user.setHead_pic(saveName);
        userMapper.save(user);
    }

    @Override
    public void editUser(int id, Model model, HttpSession session) {
        User user = userMapper.get(id);
//        model.addAttribute("number", new Random().nextInt(10000));
        model.addAttribute("user", user);
        session.setAttribute("head_pic_spare", user.getHead_pic());
    }

    @Override
    public void updateUser(UserIncludeFile userIncludeFile, HttpSession session) throws IOException {

        String saveName;
        if (userIncludeFile.getHead_pic().getOriginalFilename().equals("")) {
            saveName = (String) session.getAttribute("head_pic_spare");
        } else {
            String realname = userIncludeFile.getHead_pic().getOriginalFilename();
//        获取图片后缀
            String suffix = realname.substring(realname.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            saveName = uuid + suffix;
//            String path = ResourceUtils.getFile("classpath:static").getPath();
            File file = new File(path + "/img/upload/head/" + saveName);
//        将图片的路径存入user对象中，再用user改数据
            MultipartFile head_pic = userIncludeFile.getHead_pic();
            FileCopyUtils.copy(head_pic.getInputStream(), Files.newOutputStream(file.toPath()));
//            head_pic.transferTo(file);
        }
        User user = new User(userIncludeFile);

        user.setHead_pic(saveName);
        userMapper.update(user);
    }

    @Override
    public void deleteUser(User user) {
        userMapper.delete(user.getId());
    }

    @Override
    public void listUser(Model m, int start, int size) {
        PageHelper.startPage(start, size, "id asc");
        logger.info("start:" + start + ",size:" + size);
        List<User> users = userMapper.findAll();
        PageInfo<User> page = new PageInfo<>(users);
        m.addAttribute("page", page);
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        // 设置本地文件导出下载 的文件名
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("用户导出表.xlsx", "UTF-8"));
        //1. 获得所有用户：list
        List<User> list = userMapper.findAll();
        //2. 使用hutool代码，将list写出
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // writer 设置列宽，标题。
        writer.write(list, true);
        writer.flush(response.getOutputStream());
        writer.close();
        // writer(response.)
    }
}
