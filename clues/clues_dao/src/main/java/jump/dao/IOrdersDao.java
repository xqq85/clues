package jump.dao;

import jump.domain.Member;
import jump.domain.Orders;
import jump.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IOrdersDao {

    @Select("select * from orders")
    public List<Orders> findAll() throws Exception;

    //多表操作
    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "orderNum", column = "orderNum"),
            @Result(property = "orderTime", column = "orderTime"),
            @Result(property = "orderStatus", column = "orderStatus"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "peopleCount", column = "peopleCount"),
            @Result(property = "payType", column = "payType"),
            @Result(property = "orderDesc", column = "orderDesc"),
            @Result(property = "product", column = "productId", javaType = Product.class, one = @One(select = "jump.dao.IProductDao.findById")),
            @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select = "jump.dao.IMemberDao.findById")),
            @Result(property = "travellers",column = "id",javaType = List.class,many = @Many(select = "jump.dao.ITravellerDao.findByOrdersId"))
    })
    public Orders findById(int ordersId) throws Exception;

    @Insert("insert into orders(orderNumber,productNumber,productName,cNumber,clientName,orderTime,payType,orderStatus,orderDesc,trackNumber) " +
            "values(#{orderNumber},#{productNumber},#{productName},#{cNumber},#{clientName},#{orderTime},#{payType},#{orderStatus},#{orderDesc},#{trackNumber})")
    void insert(Orders orders) throws Exception;

    @Select("select orderNumber from orders")
    List<String> selectOrderNumber();

    @Select("select * from orders where trackNumber=#{username}")
    List<Orders> findAllByUsername(String username) throws Exception;
}
