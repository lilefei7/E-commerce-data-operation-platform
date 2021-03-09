package com.lefei.demo1.pojo;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author le
 * date:    2020/12/18
 * describe：
 */
public class UserIncludeFile{

    private int id;
    private String username;
    private String password;
    private int age;
    private String mobile;
    private String email;
    private String address;
    private int status;
    private MultipartFile head_pic;

    public UserIncludeFile(int id, String username, String password, int age, String mobile, String email, String address, int status, MultipartFile head_pic) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.status = status;
        this.head_pic = head_pic;
    }

    public UserIncludeFile() {
    }

    @Override
    public String toString() {
        return "UserIncludeFile{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", head_pic=" + head_pic +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MultipartFile getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(MultipartFile head_pic) {
        this.head_pic = head_pic;
    }
}
