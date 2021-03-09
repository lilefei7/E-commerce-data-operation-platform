package com.lefei.demo1.service;

import com.lefei.demo1.pojo.Product;
import com.lefei.demo1.pojo.ProductIncludeFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author le
 * date:    2020/12/23
 * describe：
 */
public interface ProductService {
    void batchDelete(String[] ids) ;
    void offShelf(int id) ;
    void onShelf(int id) ;
    void deleteProduct( Product product) ;
    void addProduct(ProductIncludeFile productIncludeFile) throws IOException;
    void editProduct(int id, String pic_url_spare, Model model);
    void updateProduct(ProductIncludeFile productIncludeFile, String pic_url_spare) throws IOException;
    void listProduct( Model m, int start, int size);
    void analysisData(Model m, int start, int size);
    void exportProduct(HttpServletResponse response) throws IOException;
}
