package com.lefei.demo1.controller;

import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author le
 * date:    2020/12/26
 * describe：
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CaptchaControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    Logger logger;
    MockMvc mockMvc;
    MockHttpSession session;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }
    @AfterEach
    void tearDown() {
    }
    @Test@ApiOperation("获取验证码")
    void kaptcha() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/captcha.jpg"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.request().sessionAttribute("captcha","1111"))
                .andExpect(MockMvcResultMatchers.content().contentType("image/jpeg"))
                .andReturn();
    }
}