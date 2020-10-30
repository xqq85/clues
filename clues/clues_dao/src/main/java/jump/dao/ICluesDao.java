package jump.dao;

import jump.domain.Clues;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ICluesDao {

    @Select("select * from clues")
    List<Clues> findAll() throws Exception;

    @Select("select * from clues where entryNumber=#{username}")
    List<Clues> findAllByUsername(String username) throws Exception;

    @Insert("insert into clues(cNumber,cluesName,mobile,email,address,entryTime,transform,trackNumber,entryNumber,cluesDesc) " +
            "values(#{cNumber},#{cluesName},#{mobile},#{email},#{address},#{entryTime},#{transform},#{trackNumber},#{entryNumber},#{cluesDesc})")
    void save(Clues clues) throws Exception;

    @Select("select * from clues where id = #{cluesId}")
    Clues selectCluesById(int cluesId) throws Exception;

    @Update("update clues set cluesName=#{cluesName},mobile=#{mobile},email=#{email},address=#{address},cluesDesc=#{cluesDesc} where id=#{id}")
    void changeById(Clues clues);

    @Update("update clues set transform=#{transform},trackNumber=#{trackNumber}where id=#{id}")
    void updateCluesTransform(Clues clues);

    @Select("select cNumber from clues")
    List<String> selectCNumber();

    @Select("select * from clues where cluesName=#{name} and mobile=#{mobile}")
    List<Clues> findByCluesNameAndMobile(@Param("name")String name, @Param("mobile")String mobile) throws Exception;
}
