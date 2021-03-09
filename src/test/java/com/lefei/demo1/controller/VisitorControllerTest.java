package com.lefei.demo1.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.builders.ModelBuilder;

import javax.servlet.http.HttpSession;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author le
 * date:    2020/12/26
 * describe：
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class VisitorControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    Logger logger;
    MockMvc mockMvc;
    private URL base;
    MockHttpSession session;

    //进行测试之前，先把验证码设置成1111
    @BeforeEach@ApiOperation("访问captcha.jpg把验证码存入session")
    void setUp() throws Exception {
  /*      用户只访问index的不访问captcha.jpg，前端并不会生成JSESSIONID，当用户访问
        /captcha.jpg的时候，前端才会生成JSESSIONID
        下面先访问/index只是为了模拟前端的操作顺序*/
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/index"))
                .andReturn();
//        访问captcha.jpg，获取session并把验证码添加到session里
        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/captcha.jpg"))
                .andReturn();
        session = (MockHttpSession) mvcResult2.getRequest().getSession();
    }
    @Test@ApiOperation("访问登录页面")
    void index() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andReturn();
    }
    @Test@ApiOperation("登录成功")
    void logonSuccess() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "123")
                        .queryParam("captcha", "1111")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"))
                .andReturn();
//        logger.info(session.getAttribute("captcha").toString());

    }
    @Test@ApiOperation("有session，但服务器没有收到验证码")
    void logon1() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "123")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("index"))
                .andReturn();
    }
    @Test@ApiOperation("没有访问登录页面就执行登录操作，这个时候没有session，验证码还没生成")
    void logon2() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "123")
                        .queryParam("captcha", "1111")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("index"))
                .andReturn();
    }
    @Test@ApiOperation("用户名或密码错误")
    void logon3() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "incorrectPassword")
                        .queryParam("captcha", "1111")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("index"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("msg","用户名或密码错误"))
                .andReturn();
        MvcResult mvcResult2 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "incorrectUsernaem")
                        .queryParam("password", "12345678")
                        .queryParam("captcha", "1111")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("index"))

                .andExpect(MockMvcResultMatchers.request().sessionAttribute("msg","用户名或密码错误"))
                .andReturn();
    }
    @Test@ApiOperation("验证码错误")
    void logon4() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "12345678")
                        .queryParam("captcha", "incorrectCaptcha")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("index"))
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("msg","验证码错误"))
                .andReturn();
        logger.info(session.getAttribute("captcha").toString());

    }
    @Test@ApiOperation("访问logout")
    void logout() throws Exception {
//        先登录
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/logon")
                        .queryParam("username", "pm")
                        .queryParam("password", "123")
                        .queryParam("captcha", "1111")
                        .session(session)
        )
                .andReturn();
//        再退出
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
//                        .get("/logout")
                        .get("/logout")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("index"))
                .andExpect(MockMvcResultMatchers.request().sessionAttributeDoesNotExist("username"))
                .andReturn();
    }
    @Test@ApiOperation("访问/noAccess")
    void noAccess() throws Exception {
        /*noAccess这个页面是在用filter过滤权限的时候，用户的session里没有username
    会把用户重定向到noAccess，现在用aop写的权限，这个页面正常情况下不会被访问
    * */
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/noAccess"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("noAccess"))
                .andReturn();
    }

    @AfterEach
    void tearDown() {}

}