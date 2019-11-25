package cn.itcast.service.module.impl;

import cn.itcast.dao.module.ModuleDao;
import cn.itcast.domain.module.Module;
import cn.itcast.domain.user.User;
import cn.itcast.service.module.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public Module findById(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return moduleDao.findById(id);
    }

    @Override
    public int save(Module module) {
        module.setId(UUID.randomUUID().toString());
        return moduleDao.save(module);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public List<Module> findUserModules(User user) {
        //1、当user的degree==0或1时
        if (user.getDegree() == 0 || user.getDegree() == 1) {
            return moduleDao.findAdmin(user.getDegree());
        } else if (user.getDegree() == 5){
            return moduleDao.findAll();
        }else {
            return moduleDao.findUserModules(user.getId());
        }
    }
    @Override
    public void updateRoleModule(String moduleIds, String roleid) {

        //先删除该角色下的所有模块
        moduleDao.deleteModuleByRoleId(roleid);
        ////接收页面传来的moduleIds,为该角色添加模块
//        System.out.println("moduleIds = " + moduleIds);
        String[] moduleIdes = moduleIds.split(",");
        for (String moduleIde : moduleIdes) {
            System.out.println("moduleIde = " + moduleIde);
            System.out.println("roleid = " + roleid);
            moduleDao.insertRoleModule(moduleIde, roleid);
        }
    }

    @Override
    public List<Module> findRoleModuleList(String roleid) {
        if (roleid == null)
            throw new IllegalArgumentException("参数错误");
        return moduleDao.findRoleModuleList(roleid);
    }

    @Override
    public int delete(String id) {
        if(id == null)
            throw new IllegalArgumentException("参数错误");
        return moduleDao.delete(id);
    }

    @Override
    public int update(Module module) {
        if(module==null)
            throw new IllegalArgumentException("参数错误");
        return moduleDao.update(module);
    }

    @Override
    public PageInfo findAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list);
    }
}
