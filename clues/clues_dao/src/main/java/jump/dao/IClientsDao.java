package jump.dao;

import jump.domain.Clients;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IClientsDao {

    @Select("select * from clients where trackNumber=#{username}")
    List<Clients> findAll(String username) throws Exception;

    @Insert("insert into clients(cNumber,clientName,mobile,email,address,transformTime,lastTime,trackNumber,clientDesc,transform) " +
            "values(#{cNumber},#{clientName},#{mobile},#{email},#{address},#{transformTime},#{lastTime},#{trackNumber},#{clientDesc},#{transform})")
    void insert(Clients client);

    @Select("select * from clients where cNumber=#{cNumber} and trackNumber=#{username}")
    Clients findByNumber(@Param("cNumber") String cNumber, @Param("username") String username);

    @Select("select * from clients where id=#{id}")
    Clients findById(int id);

    @Update("update clients set clientName=#{clientName},mobile=#{mobile},email=#{email},address=#{address},transformTime=#{transformTime},lastTime=#{lastTime},trackNumber=#{trackNumber},transform=#{transform},clientDesc=#{clientDesc} where id=#{id}")
    void updateById(Clients clients);

    @Select("select * from clients where cNumber=#{cNumber} and trackNumber=#{userNumber}")
    Clients selectClientsByNumber(@Param("cNumber") String cNumber, @Param("userNumber") String userNumber);
}
