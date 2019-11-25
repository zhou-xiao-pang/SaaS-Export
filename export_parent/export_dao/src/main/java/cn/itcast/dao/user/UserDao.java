package cn.itcast.dao.user;

import cn.itcast.domain.role.Role;
import cn.itcast.domain.user.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    //查找该企业该部门下的所有用户
//    List<User> findAll(String companyId, String deptId);
    //重载查给企业下的所有用户
    List<User> findAll(String companyId);

    //添加一个用户
    void save(User user);

    //按id 查找一个用户
    User findById(String id);

    //修改此id的信息
    void update(User user);

    //删除一个用户
    void delete(String id);

    //删除用户的所有角色
    void deleteUserRole(String id);

    //添加新的用户的角色
    void insertUserRole(@Param("roleId") String roleId,@Param("id") String id);

     //根据Email和password查询一个用户
    User login(@Param("email") String email, @Param("password") String password);

    //根据邮箱查找用户
    User findByEmail(String email);


    User findByOpenid(String openid);

    void updatePassword(@Param("password") String password,@Param("id") String id);

//    //查询该用户下的所有角色
//    List<Role> findUserRole(String id);
    List<User> findAllCompanyRoot();
}
