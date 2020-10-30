package jump.dao;

import jump.domain.PublicSea;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface IPublicSeaCluesDao {

    @Update("update public_sea set cNumber=#{cNumber},clientName=#{clientName},mobile=#{mobile},email=#{email},address=#{address},lastTime=#{lastTime},trackNumber=#{trackNumber},transform=#{transform},failueDesc=#{failueDesc},clientDesc=#{clientDesc} where id=#{id}")
    public void updateById(PublicSea publicSea) throws Exception;

    @Select("select * from public_sea where id = #{publicSeaId}")
    public PublicSea selectPublicSeaById(int publicSeaId) throws Exception;

    @Select("select * from public_sea")
    List<PublicSea> findAll() throws Exception;

    @Insert("insert into public_sea(cNumber,clientName,mobile,email,address,lastTime,trackNumber,clientDesc,failueDesc,entryTime) " +
            "values(#{cNumber},#{clientName},#{mobile},#{email},#{address},#{lastTime},#{trackNumber},#{clientDesc},#{failueDesc},#{entryTime})")
    void insert(PublicSea publicSea) throws Exception;

    @Select("select trackNumber from public_sea where clientName = #{clientName}")
    List<String> selectTrackNumberByclientName(String clientName) throws Exception;

    @Select("select cNumber from public_sea")
    List<String> selectCNumber() throws Exception;

    @Insert("insert into public_sea(cNumber,clientName,mobile,email,address,entryTime,lastTime,transform,entryNumber,clientDesc) " +
            "values(#{cNumber},#{clientName},#{mobile},#{email},#{address},#{entryTime},#{lastTime},#{transform},#{entryNumber},#{clientDesc})")
    void save(PublicSea publicSea) throws Exception;

    @Select("select * from public_sea where clientName = #{clientName} and mobile = #{mobile}")
    List<PublicSea> findPublicSeaByNameAndMobile(@Param("clientName")String clientName, @Param("mobile")String mobile);
}
