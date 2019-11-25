package cn.itcast.web.controller.user;

import cn.itcast.common.common.utils.MailUtil;
import cn.itcast.domain.dept.Dept;
import cn.itcast.domain.role.Role;
import cn.itcast.domain.user.User;
import cn.itcast.service.dept.DeptService;
import cn.itcast.service.role.RoleService;
import cn.itcast.service.user.UserService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService service;

    @Autowired
    private DeptService deptService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(path = "/list",name = "查看所有用户的列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        PageInfo<User> pageInfo = service.findAll(companyId, page, size);
        request.setAttribute("page", pageInfo);
        return "/system/user/user-list";
    }

    @RequestMapping(path = "/toAdd",name = "前往添加用户的页面")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "/system/user/user-add";
    }

    @RequestMapping(path = "/edit",name = "添加或修改用户的信息")
    public String edit(User user) throws Exception {
        String password = user.getPassword();
        if (StringUtils.isEmpty(user.getId())) {
//            System.out.println("user = " + user);
            user.setCompanyId(companyId);
            user.setCompanyName(companyName);
            service.save(user);
            //发送邮件一定要在添加用户之后
            //使用的密码为未加密的密码
            MailUtil.sendMsg(user.getEmail(), "欢迎使用SaaS-Export管理系统", "您的用户名为" + user.getEmail() + "您的密码为：" + password);
        }else {
            service.update(user);
        }

        return "redirect:/system/user/list.do";
    }

    @RequestMapping(path = "/toUpdate",name = "前往修改用户页面")
    public String toUpdate(String id){
        User user = service.findById(id);
        request.setAttribute("user", user);
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "/system/user/user-update";
    }

    @RequestMapping(path = "/delete",name = "删除用户信息")
    public String delete(String id){
        service.delete(id);
        return "redirect:/system/user/list.do";
    }

    @RequestMapping(path = "/roleList",name = "查看用户角色列表")
    public String toRoleList(String id){
        User user = service.findById(id);
        request.setAttribute("user", user);
        List<Role> list = roleService.findAll(companyId);
        request.setAttribute("roleList", list);
        List<Role> userRole = roleService.findUserRole(id);
        String userRoleStr = "";
        for (Role role : userRole) {
            userRoleStr += role.getId();
            userRoleStr += ",";
        }
        request.setAttribute("userRoleStr",userRoleStr);
        return "/system/user/user-role";
    }

    @RequestMapping(path = "/changeRole",name = "修改用户的角色")
    public String changeRole(String userid,String[] roleIds){
        service.changeRole(userid, roleIds);
        return "redirect:/system/user/list.do";
    }

    @RequestMapping("/toUpdatePassword")
    public String toUpdatePassword(){
        request.setAttribute("user", loginUser);
        return "/system/user/user-password";
    }

    @RequestMapping("/updatePassword")
    public String updatePassword(User user){
        service.updatePassword(user);
        return "redirect:/login.do";
    }
}
