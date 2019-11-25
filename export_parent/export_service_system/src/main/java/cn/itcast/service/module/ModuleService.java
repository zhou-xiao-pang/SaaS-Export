package cn.itcast.service.module;

import cn.itcast.domain.module.Module;
import cn.itcast.domain.user.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {
    //分页查询模块信息
    PageInfo findAll(int page, int size);

    //用于下拉列表
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



    void updateRoleModule(String moduleIds, String roleid);

    //   //根据用户动态的生成列表
    List<Module> findUserModules(User user);
}
