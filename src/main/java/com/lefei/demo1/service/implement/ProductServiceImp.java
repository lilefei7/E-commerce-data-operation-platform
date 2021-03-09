package com.lefei.demo1.service.implement;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author le
 * date:    2020/12/24
 * describe：
 */
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    Logger logger;
    @Value("${myPath}")
    String path;

    @Override
    public void batchDelete(String[] ids) {
        productMapper.batchDelete(ids);
    }

    @Override
    public void offShelf(int id) {
        productMapper.offShelf(id);


    }

    @Override
    public void onShelf(int id) {
        Date addDate = new Date(new java.util.Date().getTime());
        productMapper.onShelf(id,addDate);
    }

    @Override
    public void deleteProduct(Product product) {
            productMapper.delete(product.getId());
    }

    @Override
    public void addProduct(ProductIncludeFile productIncludeFile) throws IOException {
        String realname = productIncludeFile.getPic_url().getOriginalFilename();
//        获取图片后缀
//        String realname = "a.jpg";
        String suffix = realname.substring(realname.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveName = uuid + suffix;
//        String path = ResourceUtils.getFile("classpath:static").getPath();
        File file = new File(path + "/img/upload/product/" + saveName);
        MultipartFile pic_url = productIncludeFile.getPic_url();
        FileCopyUtils.copy(pic_url.getInputStream(), Files.newOutputStream(file.toPath()));
//        pic_url.transferTo(file);
//        测试的时候抛出异常UnsupportedOperationException
        Product product = new Product(productIncludeFile);
        product.setAddDate(new Date(new java.util.Date().getTime()));
        product.setPic_url(saveName);
        productMapper.save(product);
    }

    @Override
    public void editProduct(int id, String pic_url_spare, Model model) {
        Product product = productMapper.get(id);
        model.addAttribute("number", new Random().nextInt(10000));
        model.addAttribute("product", product);
//        从商品列表页面将数据库中商品的图片地址传入商品编辑界面，
//        如果用户在更改商品信息的时候没有传图片，就用这个地址
        model.addAttribute("pic_url_spare", pic_url_spare);
    }

    @Override
    public void updateProduct(ProductIncludeFile productIncludeFile, String pic_url_spare) throws IOException {
        String saveName;
        if (productIncludeFile.getPic_url()==null||productIncludeFile.getPic_url().getOriginalFilename().equals("")) {
//            用户在更改信息的时候没有选择图片
            saveName = pic_url_spare;
        } else {
            String realname = productIncludeFile.getPic_url().getOriginalFilename();
//        获取图片后缀
            String suffix = realname.substring(realname.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString();
            saveName = uuid + suffix;
//            String path = ResourceUtils.getFile("classpath:static").getPath();
            File file = new File(path + "/img/upload/product/" + saveName);
            MultipartFile pic_url = productIncludeFile.getPic_url();
            FileCopyUtils.copy(pic_url.getInputStream(), Files.newOutputStream(file.toPath()));
//        pic_url.transferTo(file);
//        测试的时候抛出异常UnsupportedOperationException
        }
        Product product = new Product(productIncludeFile);
        product.setPic_url(saveName);
        productMapper.update(product);
        logger.info("product" + product.toString());
    }

    @Override
    public void listProduct(Model m, int start, int size) {
        PageHelper.startPage(start, size, "id asc");
        List<Product> products = productMapper.findAll();
        PageInfo<Product> page = new PageInfo<>(products);
        m.addAttribute("page", page);
    }

    @Override
    public void analysisData(Model m, int start, int size) {
        PageHelper.startPage(start, size, "visitCount desc");
        List<Product> products = productMapper.findAll();
        PageInfo<Product> pagePopular = new PageInfo<>(products);
        m.addAttribute("pagePopular", pagePopular);
//        把start传到前端，用来计算排名
        m.addAttribute("start", start);
        m.addAttribute("size", size);
        PageHelper.startPage(start, size, "sum asc");
        List<Product> products2 = productMapper.findAll();
        PageInfo<Product> pageLacking = new PageInfo<>(products2);
        m.addAttribute("pageLacking", pageLacking);
    }

    @Override
    public void exportProduct(HttpServletResponse response) throws IOException {
        // 设置本地文件导出下载 的文件名
        response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("商品导出表.xlsx", "UTF-8"));
        //1. 获得所有商品：list
        List<Product> list = productMapper.findAll();
//        List<Product> list = pdao.selectAll();
        //2. 使用hutool代码，将list写出
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // writer 设置列宽，标题。
        writer.write(list, true);
        writer.flush(response.getOutputStream());
        writer.close();
        // writer(response.)
    }
}
