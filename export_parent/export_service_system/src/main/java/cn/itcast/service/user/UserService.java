package cn.itcast.service.user;

import cn.itcast.domain.role.Role;
import cn.itcast.domain.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    //查找该企业该部门下的所有用户
//    PageInfo<User> findAll(String companyId, String deptId, int page, int size);
    //查找该企业所有用户
    PageInfo<User> findAll(String companyId, int page, int size);

    //添加一个用户
    void save(User user);

    //按id查找一个用户
    User findById(String id);

    //修改id为这个的用户信息
    void update(User user);

    //删除一个用户
    void delete(String id);

    //更新用户的角色
    void changeRole(String id, String[] roleIds);

//   //根据Email和password查询一个用户
    User login(String email, String password);

    //根据邮箱查找用户
    User findByEmail(String email);

    //根据openid查找用户
    User findByOpenid(String openid);

    //修改用户密码
    void updatePassword(User user);

//    //查询该用户的所有角色
//    List<Role> findUserRole(String id);

    PageInfo findAllCompanyRoot(int page,int size);
}
