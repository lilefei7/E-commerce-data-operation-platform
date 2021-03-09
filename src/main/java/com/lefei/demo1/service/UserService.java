package com.lefei.demo1.service;

import com.lefei.demo1.pojo.User;
import com.lefei.demo1.pojo.UserIncludeFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author le
 * date:    2020/12/23
 * describe：
 */
public interface UserService {
    void addUser(UserIncludeFile userIncludeFile) throws IOException;
    void editUser(int id, Model model, HttpSession session);
    void updateUser(UserIncludeFile userIncludeFile, HttpSession session) throws IOException;
    void deleteUser(User user);
    void listUser(Model m,int start,  int size);
    void export(HttpServletResponse response) throws IOException;
}
