package cn.itcast.dao.module;

import cn.itcast.domain.module.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleDao {
    //分页查询模块信息
    List<Module> findAll();

    //添加一个模块
    int save(Module module);

    //按id查找一个模块
    Module findById(String id);

    //更新一个模块信息
    int update(Module module);

    //删除一个模块
    int delete(String id);

    //根据roleid查询改角色下现有的模块
    List<Module> findRoleModuleList(String roleid);

    //删除改角色下的所有模块
    void deleteModuleByRoleId(String roleid);

    //为该角色添加这个模块
    void insertRoleModule(@Param("moduleIde") String moduleIde, @Param("roleid") String roleid);

    //查询平台管理员的菜单
    List<Module> findAdmin(Integer belong);

    //根据用户id查询他的菜单
    List<Module> findUserModules(String id);
}
