package jump.dao;

import jump.domain.Role;
import jump.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IUserDao {

    @Select("select * from users where username=#{username}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles",column = "id",javaType = List.class,many = @Many(select = "jump.dao.IRoleDao.findRoleByUserId"))
    })
    public UserInfo findByUsername(String username) throws Exception;

    @Select("select * from users")
    List<UserInfo> findAll() throws Exception;

    @Insert("insert into users(email,username,name,password,phoneNum,status) values(#{email},#{username},#{name},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo) throws Exception;

    @Select("select * from users where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "phoneNum", column = "phoneNum"),
            @Result(property = "status", column = "status"),
            @Result(property = "roles",column = "id",javaType = List.class,many = @Many(select = "jump.dao.IRoleDao.findRoleByUserId"))
    })
    UserInfo findById(int id) throws Exception;

    @Select("select * from role where id not in (select roleId from users_role where userId=#{userId})")
    List<Role> findOtherRoles(int userId);

    @Insert("insert into users_role(userId,roleId) values(#{userId},#{roleId})")
    void addRoleToUser(@Param("userId") int userId, @Param("roleId") int roleId);

    @Update("update users set username=#{username},name=#{name},email=#{email},phoneNum=#{phoneNum},STATUS=#{status} where id=#{id}")
    void updateById(UserInfo userInfo);

    @Delete({
            "<script>",
            "delete",
            "from users",
            "where id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteUsersByIds(@Param("ids") int[] ids) throws Exception;

    @Delete({
            "<script>",
            "delete",
            "from users_role",
            "where userId in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    void deleteUsersRoleByIds(@Param("ids") int[] ids) throws Exception;

    @Select("select users.username from users,users_role,role where users.id=users_role.userId and users_role.roleId = role.id and role.roleName = 'USER'")
    List<String> findTrackNumber();
}
