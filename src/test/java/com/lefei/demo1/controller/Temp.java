package com.lefei.demo1.controller;

import com.jhlabs.image.MarbleTexFilter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author le
 * date:    2020/12/30
 * describe：
 */
public class Temp {

    public static void main(String arg[]) throws IOException {
         String path="C:\\Users\\le\\Desktop\\SCD\\src\\main\\resources\\static";
        File file = new File(path + "/img/upload/product/blueHat.jpg");
        System.out.println(file.getPath());
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile(
                        "pic_url", "blueHat1.jpg",
//                        "text/plain", fileInputStream);
                        "multipart/form-data", fileInputStream);
        File file2 = new File(path + "/img/upload/product/blueHatNew1.jpg");
        System.out.println(file2.getPath());
        MultipartFile pic_url =   mockMultipartFile;
        pic_url.transferTo(file2);
        System.out.println("end");
    }
}
