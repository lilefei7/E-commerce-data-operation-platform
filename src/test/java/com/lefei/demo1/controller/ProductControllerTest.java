package com.lefei.demo1.controller;

import com.lefei.demo1.mapper.ProductMapper;
import com.lefei.demo1.pojo.Product;
import com.lefei.demo1.pojo.ProductIncludeFile;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Date;

/**
 * @author le
 * date:    2020/12/26
 * describe：
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class ProductControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    Logger logger;
    @Autowired
    ProductMapper productMapper;
    MockMvc mockMvc;
    private URL base;
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

    @AfterEach
    void tearDown() {
    }

    @Test
    @ApiOperation("删除产品")
    @Transactional
    void batchDelete() throws Exception {
        MvcResult mvcResult1 = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/batchDelete")
                        .queryParam("ids[]", "75,76")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"))
                .andReturn();
        Assertions.assertEquals(productMapper.get(75), null);
        Assertions.assertEquals(productMapper.get(76), null);
    }

    @Test
    @Transactional
    @ApiOperation("下架商品")
    void offShelf() throws Exception {
//        原来是上架状态1，之后是下架状态0
        Product product = productMapper.get(77);
        Assertions.assertEquals(1, product.getStatus());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/offShelf")
                        .queryParam("id", "77")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"));
        product = productMapper.get(67);
        Assertions.assertEquals(0, product.getStatus());
    }

    @Test
    @Transactional
    @ApiOperation("上架商品")
    void onShelf() throws Exception {
        Product product = productMapper.get(78);
        Assertions.assertEquals(0, product.getStatus());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/onShelf")
                        .queryParam("id", "78")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"));
        product = productMapper.get(78);
        Assertions.assertEquals(1, product.getStatus());
    }

    @Test
    @ApiOperation("添加商品")
    @Transactional
    void addProduct() throws Exception {
        Product product = new Product(-1, "productTest81", 200, 1000, 122, 1, new Date(new java.util.Date().getTime()), "123", "abc.jpg");
//        String path = ResourceUtils.getFile("classpath:static").getPath();
        File file = new File(path + "/img/upload/product/blueHat.jpg");
//        File file = new File("C:\\Users\\le\\Desktop\\caom.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "pic_url", "blueHat1.jpg",
                        "text/plain", fileInputStream);
//        new MockMultipartFile("pic_url", file.getName(), "text/plain", IOUtils.toByteArray(fileInputStream));
//        System.out.println(FileUtil.getBytesByFile(file.getPath()).length);
//        执行添加操作
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/addProduct")
                        .file(mockMultipartFile)
                        .param("key", "pic_url")
                        .param("name", String.valueOf(product.getName()))
                        .param("price", String.valueOf(product.getPrice()))
                        .param("sum", String.valueOf(product.getSum()))
                        .param("visitCount", String.valueOf(product.getVisitCount()))
                        .param("status", String.valueOf(product.getStatus()))
                        .param("description", String.valueOf(product.getDescription()))
                        .session(session)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"))
                .andReturn();
    }

    @Test
    @ApiOperation("删除商品")
    @Transactional
    void deleteProduct() throws Exception {
        Product product = productMapper.get(79);
        Assertions.assertNotEquals(null, product);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/deleteProduct")
//                        .queryParams()
//                        controller的参数应该是Product，但是只传id也可以测试
                        .queryParam("id", "79")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"));
        product = productMapper.get(79);
        Assertions.assertEquals(null, product);
    }

    @Test
    @ApiOperation("返回编辑用户的页面")
    void editProduct() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/editProduct")
                        .queryParam("id", "79")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/editProduct"));
    }

    @Test
    @ApiOperation("编辑商品")
    @Transactional
    void updateProduct() throws Exception {
//生成图片文件mockMultipartFile
//        String path = ResourceUtils.getFile("classpath:static").getPath();
        File file = new File(path + "/img/upload/product/blueHat.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "pic_url", "blueHat.jpg",
                        "image/jpeg", fileInputStream);
//        将图片文件存入对象
        ProductIncludeFile product = new ProductIncludeFile(79, "productTest80",
                80, 80, 80, 80,
                new Date(new java.util.Date().getTime()), "testID80",
                mockMultipartFile);
//执行更新操作
        mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/updateProduct")
                        .file(mockMultipartFile)
                        .param("id", String.valueOf(product.getId()))
                        .param("name", String.valueOf(product.getName()))
                        .param("description", String.valueOf(product.getDescription()))
                        .param("price", String.valueOf(product.getPrice()))
                        .param("sum", String.valueOf(product.getSum()))
                        .param("visitCount", String.valueOf(product.getVisitCount()))
                        .param("status", String.valueOf(product.getStatus()))
                        .param("pic_url_spare", "abcdefghijklmnopqrstuvwxyz.jpg")
                        .session(session)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("listProduct"));
        Product productNew = productMapper.get(79);
//        获取到修改之后的图片文件mockMultipartFileNew，
//        和之前生成的图片文件mockMultipartFile做对比
        System.out.println(productNew.getPic_url());
        Assertions.assertNotEquals(null, product);
//        更改过的数据应该相等，未更改过的应该不等
        Assertions.assertEquals(product.getName(), productNew.getName());
        Assertions.assertEquals(product.getDescription(), productNew.getDescription());
        byte[] bytes = ((MultipartFile) mockMultipartFile).getBytes();
//        byte[] bytes1 = mockMultipartFileNew.getBytes();
//        Assertions.assertEquals(Arrays.toString(bytes).equals(string), true);
        Assertions.assertNotEquals(product.getSum(), productNew.getSum());
        Assertions.assertNotEquals(product.getStatus(), productNew.getStatus());
    }

    @Test
    @ApiOperation("商品列表")
    void listProduct() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/listProduct")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/listProduct"));
    }

    @Test
    @ApiOperation("数据分析")
    void analysisData() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/analysisData")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/analysisData"));

    }

    @Test
    @ApiOperation("导出数据")
    void exportProduct() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/exportProduct")
                        .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("content-disposition"))
        ;
    }

    @Test
    @ApiOperation("test1")@Disabled
    void test1() throws Exception {
        File file = new File(path + "/img/upload/product/blueHat.jpg");
        logger.info(file.getPath());
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "pic_url", "blueHat1.jpg",
//                        "text/plain", fileInputStream);
                        "multipart/form-data", fileInputStream);
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .multipart("/test1")
                        .file(mockMultipartFile)
//                        .session(session)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        )
                .andReturn();
    }

}