package com.lefei.demo1.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @author le
 * date:    2020/12/18
 * describe：
 */
public class Product {
    private int id;
    private String name;
    private int price;
    private int sum;
    private int visitCount;
    private int status;
    private Date addDate;
    private String description;
    private String pic_url;

    public Product(ProductIncludeFile productIncludeFile) {
        this.id = productIncludeFile.getId();
        this.name = productIncludeFile.getName();
        this.price = productIncludeFile.getPrice();
        this.sum = productIncludeFile.getSum();
        this.visitCount = productIncludeFile.getVisitCount();
        this.status = productIncludeFile.getStatus();
        this.description = productIncludeFile.getDescription();
        this.pic_url = productIncludeFile.getPic_url().getOriginalFilename();
    }
    public Product() {
    }

    public Product(int id, String name, int price, int sum, int visitCount, int status, Date addDate, String description, String pic_url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sum = sum;
        this.visitCount = visitCount;
        this.status = status;
        this.addDate = addDate;
        this.description = description;
        this.pic_url = pic_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", sum=" + sum +
                ", visitCount=" + visitCount +
                ", status=" + status +
                ", addDate=" + addDate +
                ", description='" + description + '\'' +
                ", pic_url='" + pic_url + '\'' +
                '}';
    }
}
