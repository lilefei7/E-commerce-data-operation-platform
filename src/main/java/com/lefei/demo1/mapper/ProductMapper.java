package com.lefei.demo1.mapper;

import com.lefei.demo1.pojo.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
@Mapper
public interface ProductMapper {

    @Select("select * from t_product")
    List<Product> findAll();

    @Insert("insert into t_product ( name,price,sum,visitCount,status,addDate,description,pic_url ) " +
            "values (#{name},#{price},#{sum},#{visitCount},#{status},#{addDate},#{description},#{pic_url}) ")
    int save(Product Product);

    @Delete("delete from t_product where id= #{id} ")
    void delete(int id);

    @Select("select * from t_product where id= #{id} ")
    Product get(int id);

    @Update("update t_product set name=#{name}, price=#{price}, " +
            "sum=#{sum}, visitCount=#{visitCount}, status=#{status}, " +
            "description=#{description}, pic_url=#{pic_url} where id=#{id} ")
    int update(Product t_product);
    @Update("update t_product set status=0 where id=#{id} ")
    int offShelf(int id);
    @Update("update t_product set status=1,addDate=#{addDate} where id=#{id} ")
    int onShelf(int id, Date addDate);

    @Delete("<script> delete from t_product where id in <foreach collection='array' item='id' open='(' separator=',' close=')'>#{id}</foreach> </script>")
    void batchDelete(String[] ids);
}