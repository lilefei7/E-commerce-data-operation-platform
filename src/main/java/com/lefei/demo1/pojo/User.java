package com.lefei.demo1.pojo;

/**
 * @author le
 * date:    2020/12/18
 * describe：
 */
public class User {

    private int id;
    private String username;
    private String password;
    private int age;
    private String mobile;
    private String email;
    private String address;
    private int status;
    private String head_pic;

    public User() {
    }

    public User(int id, String username, String password, int age, String mobile, String email, String address, int status, String head_pic) {
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
    public User(UserIncludeFile userIncludeFile) {
        this.id = userIncludeFile.getId();
        this.username = userIncludeFile.getUsername();
        this.password = userIncludeFile.getPassword();
        this.age = userIncludeFile.getAge();
        this.mobile = userIncludeFile.getMobile();
        this.email = userIncludeFile.getEmail();
        this.address = userIncludeFile.getAddress();
        this.status = userIncludeFile.getStatus();
        this.head_pic = userIncludeFile.getHead_pic().getOriginalFilename();
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

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", head_pic='" + head_pic + '\'' +
                '}';
    }
}
