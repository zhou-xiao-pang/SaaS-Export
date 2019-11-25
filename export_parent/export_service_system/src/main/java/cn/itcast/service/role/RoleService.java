package cn.itcast.service.role;

import cn.itcast.domain.role.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    //分页查找给企业的所有的角色（职位）
    PageInfo<Role> findAll(String company, int page, int size);

    //添加一个角色
    int save(Role role);

    //根据id查找一个角色
    Role findById(String id);

    //更新角色信息
    int update(Role role);

    //删除角色
    int delete(String id);

    //查询用户的角色
    List<Role> findUserRole(String id);

    //查询所有的角色
    List<Role> findAll(String companyId);
}
