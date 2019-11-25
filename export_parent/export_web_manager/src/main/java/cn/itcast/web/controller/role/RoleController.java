package cn.itcast.web.controller.role;

import cn.itcast.domain.module.Module;
import cn.itcast.domain.role.Role;
import cn.itcast.service.module.ModuleService;
import cn.itcast.service.role.RoleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;


    @RequestMapping(path = "/list",name = "查看所有的角色列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5")int size){
        PageInfo<Role> pageInfo = roleService.findAll(companyId, page, size);
        request.setAttribute("page", pageInfo);
        return "system/role/role-list";
    }

    @RequestMapping(path = "/toAdd",name = "前往添加角色页面")
    public String toAdd() {
        return "/system/role/role-add";
    }

    @RequestMapping(path = "/edit",name = "添加或修改角色信息")
    public String save(Role role) {
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if (StringUtils.isEmpty(role.getId())) {
            roleService.save(role);
        }else {
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    @RequestMapping(path = "/toUpdate",name = "前往修改角色页面")
    public String toUpdate(String id){
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        return "system/role/role-update";
    }

    @RequestMapping(path = "/delete",name = "删除一个角色信息")
    public String delete(String id){

        roleService.delete(id);
        return "redirect:/system/role/list.do";

    }

    @RequestMapping(path = "/roleModule",name = "角色页面跳转到角色配置权限页面")
    public String roleModule(String roleid){

        //根据roleid查找该角色下的拥有的模块
        Role role = roleService.findById(roleid);
        request.setAttribute("role", role);
        return "system/role/role-module";
    }

    @RequestMapping(path = "/getZtreeNodes")
    public @ResponseBody List<Map> getZtreeNodes(String roleid){
        List<Map> zNodes = new ArrayList<Map>();
        //查询所有的模块
        List<Module> moduleList = moduleService.findAll();

        //根据roleid查询改角色下现有的模块
        List<Module> roleModuleList = moduleService.findRoleModuleList(roleid);
        for (Module module : moduleList) {
            Map map = new HashMap();
            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());

            //判断角色下是否拥有改模块，有的话添加check属性
            if (roleModuleList.contains(module)) {
                map.put("checked", true);
            }
            zNodes.add(map);
        }
        return zNodes;
    }

    @RequestMapping(path = "/updateRoleModule",name = "修改角色的权限")
    public String updateRoleModule(String moduleIds,String roleid) {
        //先删除该角色下的所有模块
        moduleService.updateRoleModule(moduleIds, roleid);
        //接收页面传来的moduleIds,为该角色添加模块

        return "redirect:/system/role/list.do";
    }

}
