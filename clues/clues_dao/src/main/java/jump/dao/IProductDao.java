package jump.dao;

import jump.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IProductDao {

    //查询所有的产品信息
    @Select("select * from product")
    public List<Product> findAll() throws Exception;

    @Insert("insert into product(productNumber,productName,location,productTime,productPrice,productDesc,productStatus) " +
            "values(#{productNumber},#{productName},#{location},#{productTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product);

    @Select("select * from product where id = #{productId}")
    Product selectById(int productId);

    @Update("update product set productNumber=#{productNumber},productName=#{productName},location=#{location},productTime=#{productTime},productPrice=#{productPrice},productStatus=#{productStatus},productDesc=#{productDesc} where id = #{id}")
    void updateById(Product product) throws Exception;
}
