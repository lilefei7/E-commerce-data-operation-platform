package com.lefei.demo1.controller;

import com.lefei.demo1.mapper.ProductMapper;
import com.lefei.demo1.mapper.UserMapper;
import com.lefei.demo1.pojo.Product;
import com.lefei.demo1.pojo.User;
import com.lefei.demo1.pojo.UserIncludeFile;
import com.lefei.demo1.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author le
 * date:    2020/12/26
 * describe：
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    Logger logger;
    @Autowired
    UserMapper userMapper;
    MockMvc mockMvc;
    MockHttpSession session;
    @Value("${myPath}")
    String path;

    @BeforeEach
    @ApiOperation("先登录")
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/index"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
//        访问captcha.jpg，获取session并把验证码添加到session里
        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/captcha.jpg"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("captcha", "1111"))
                .andExpect(MockMvcResultMatchers.content().contentType("image/jpeg"))
                .andReturn();
        session = (MockHttpSession) mvcResult2.getRequest().getSession();

        MvcResult mvcResult3 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "123")
                        .queryParam("captcha", "1111")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("username", "pm"))
                .andReturn();

    }

    @Test
    @ApiOperation("添加用户")
//    @Disabled
    @Transactional
    void addUser() throws Exception {
        User userOld = userMapper.logon("lilefei", "password");
        Assertions.assertEquals(null, userOld);
        File file = new File(path + "/img/upload/head/blueHat.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "head_pic", "blueHat.jpg",
                        "text/plain", fileInputStream);
        UserIncludeFile userIncludeFile = new UserIncludeFile(100, "lilefei", "password", 21, "17703741864", "ailef@foxmail.com", "河南郑州", 1, mockMultipartFile);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/addUser")
                        .file(mockMultipartFile)
                        .param("username", userIncludeFile.getUsername())
                        .param("password", String.valueOf(userIncludeFile.getPassword()))
                        .param("age", String.valueOf(userIncludeFile.getAge()))
                        .param("mobile", String.valueOf(userIncludeFile.getMobile()))
                        .param("email", String.valueOf(userIncludeFile.getEmail()))
                        .param("address", String.valueOf(userIncludeFile.getAddress()))
                        .param("status", String.valueOf(userIncludeFile.getStatus()))
                        .session(session)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listUser"))
                .andReturn();
        User userNew = userMapper.logon("lilefei", "password");
        Assertions.assertNotEquals(null, userNew);
    }

    @Test
    @ApiOperation("返回更改用户的界面")
//    @Disabled
    void editUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/editUser")
                        .queryParam("id", "20")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/editUser"));

    }

    @Test
    @ApiOperation("更改用户")
    @Transactional
    void updateUser() throws Exception {
//        User userOld = userMapper.logon("lilefei","password");
//        Assertions.assertEquals(null,userOld);
        File file = new File(path + "/img/upload/head/blueHat.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "head_pic", "blueHat.jpg",
                        "text/plain", fileInputStream);
        UserIncludeFile userIncludeFile = new UserIncludeFile(666, "2lilefei", "password", 21, "17703741864", "ailef@foxmail.com", "河南郑州", 1, mockMultipartFile);
        User user = userMapper.get(666);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/updateUser")
                        .file(mockMultipartFile)
                        .param("id", String.valueOf(userIncludeFile.getId()))
                        .param("username", String.valueOf(userIncludeFile.getUsername()))
                        .param("password", String.valueOf(userIncludeFile.getPassword()))
                        .param("age", String.valueOf(user.getAge()))
                        .param("mobile", String.valueOf(user.getMobile()))
                        .param("email", String.valueOf(userIncludeFile.getEmail()))
                        .param("address", String.valueOf(userIncludeFile.getAddress()))
                        .param("status", String.valueOf(userIncludeFile.getStatus()))
                        .session(session)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listUser"))
                .andReturn();
        User userNew = userMapper.get(666);
        Assertions.assertEquals(userIncludeFile.getUsername(), userNew.getUsername());
        Assertions.assertEquals(userIncludeFile.getPassword(), userNew.getPassword());
        Assertions.assertNotEquals(userIncludeFile.getAge(), userNew.getAge());
        Assertions.assertNotEquals(userIncludeFile.getMobile(), userNew.getMobile());

//        MvcResult mvcResult2 = mockMvc.perform(
//                MockMvcRequestBuilders
//                        .get( "/img/upload/head/" + userNew.getHead_pic()))
////                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn();
//        ServletOutputStream outputStream = mvcResult2.getResponse().getOutputStream();
//        byte[] bytesArray = new byte[1024 * 1024 * 10];
//        outputStream.write(bytesArray);
//        System.out.println(bytesArray.length);
//        System.out.println(userIncludeFile.getHead_pic().);
//        Assertions.assertEquals(userIncludeFile.getHead_pic().getBytes(), bytesArray);


    }

    @Test
    @ApiOperation("删除用户")
    @Transactional
    void deleteUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/deleteUser")
                        .queryParam("id", "79")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listUser"));

        User user = userMapper.get(79);
        Assertions.assertEquals(null, user);
    }

    @Test
    @ApiOperation("获取所有用户")
    void listUser() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listUser")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/listUser"));
    }

    @Test
    @ApiOperation("导出用户表")
    void export() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/exportUser")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("content-disposition"))
        ;
    }
}