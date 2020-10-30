package jump.service;

import jump.domain.Role;
import jump.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {
    List<UserInfo> findAll(int page, int size) throws Exception;

    void save(UserInfo userInfo) throws Exception;

    UserInfo findById(int id) throws Exception;

    List<Role> findOtherRoles(int userId) throws Exception;

    void addRoleToUser(int userId, int[] roleIds) throws Exception;

    void updateById(UserInfo userInfo) throws Exception;

    void deleteUsersByIds(int[] ids) throws Exception;

    List<String> findTrackNumber() throws Exception;
}
