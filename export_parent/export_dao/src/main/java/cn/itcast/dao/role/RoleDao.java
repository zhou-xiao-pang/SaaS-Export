package cn.itcast.dao.role;

import cn.itcast.domain.role.Role;

import java.util.List;

public interface RoleDao {
    //分页查询给企业的职位
    List<Role> findAll(String company);

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
}
