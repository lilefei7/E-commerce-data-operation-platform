package com.lefei.demo1.controller;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lefei.demo1.mapper.ProductMapper;
import com.lefei.demo1.pojo.Product;
import com.lefei.demo1.pojo.ProductIncludeFile;
import com.lefei.demo1.service.ProductService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author le
 * date:    2020/12/17
 * describe：
 */
@Controller
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    Logger logger;

    @RequestMapping(value = "/batchDelete")
    public String batchDelete(@RequestParam(value = "ids[]") String[] ids) throws Exception {
//        logger.info(ids.toString());
        productService.batchDelete(ids);
        // 删除成功就前台跳转到商品列表，下面的重定向用不到
        return "redirect:listProduct";
//        return "listProduct";
    }

    @RequestMapping("/offShelf")
    public String offShelf(int id) throws Exception {
        productService.offShelf(id);
        return "redirect:listProduct";
    }

    @RequestMapping("/onShelf")
    public String onShelf(int id) throws Exception {
        productService.onShelf(id);
        return "redirect:listProduct";
    }

    @RequestMapping("/addProduct")
    public String addProduct(ProductIncludeFile productIncludeFile) throws Exception {
        productService.addProduct(productIncludeFile);
        return "redirect:listProduct";
    }

    @RequestMapping("/deleteProduct")
    public String deleteProduct(Product product) throws Exception {
        productService.deleteProduct(product);
        return "redirect:listProduct";
    }

    //返回一个表单
    @RequestMapping("/editProduct")
    public String editProduct(int id, String pic_url_spare, Model model) throws Exception {
        productService.editProduct(id, pic_url_spare, model);
        return "admin/editProduct";
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(ProductIncludeFile productIncludeFile, String pic_url_spare) throws Exception {
        productService.updateProduct(productIncludeFile, pic_url_spare);
        return "redirect:listProduct";
    }

    @RequestMapping(value = {"/listProduct"})
    public String listProduct(Model m, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        productService.listProduct(m, start, size);
        return "admin/listProduct";
    }

    @RequestMapping(value = {"/analysisData"})
    public String analysisData(Model m, @RequestParam(value = "start", defaultValue = "1") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        productService.analysisData(m, start, size);
        return "admin/analysisData";
    }

    @RequestMapping(value = {"/exportProduct"})
    public void exportProduct(HttpServletResponse response) throws IORuntimeException, IOException {
        productService.exportProduct(response);
    }






    @Value("${myPath}")
    String path;
    @RequestMapping(value = {"/test1"})
//    @RequestParam(value = "pic_url")
    public void test1( MultipartFile pic_url ) throws IORuntimeException, IOException {
        File file = new File(path + "/img/upload/product/blueHatNew1.jpg");
//        FileCopyUtils.copy(pic_url.getInputStream(), Files.newOutputStream(file.toPath()));
        logger.info(file.getPath());
        MultipartFile pic_url1 = pic_url;
        pic_url1.transferTo(file);
        System.out.println("end");

    }
}
