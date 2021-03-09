package com.lefei.demo1.mapper;

import com.lefei.demo1.pojo.Product;
import com.lefei.demo1.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@Mapper
public interface UserMapper {

    @Select("select * from t_user")
    List<User> findAll();

    @Insert("insert into t_user (username,password,age,mobile,email,address,status,head_pic ) " +
            "values (#{username},#{password},#{age},#{mobile},#{email},#{address},#{status},#{head_pic}) ")
    int save(User t_user);

    @Delete("delete from t_user where id= #{id} ")
    void delete(int id);

    @Select("select * from t_user where username= #{username} and password= #{password}")
    User logon(String username, String password);

    @Update("update t_user set username = #{username}," +
            "password = #{password},age = #{age},mobile = #{mobile},email = #{email},address = #{address}," +
            "status = #{status},head_pic = #{head_pic} " +
            "where id = #{id}")
    int update(User t_user);

    @Select("select * from t_user where id=#{id} ")
    User get(Integer id);

}