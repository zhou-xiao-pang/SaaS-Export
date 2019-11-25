package cn.itcast.web.controller.dept;

import cn.itcast.domain.dept.Dept;
import cn.itcast.service.dept.DeptService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptService service;

    @RequestMapping(path = "/list",name = "查看所有的部门列表")
    public String list(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        PageInfo pageInfo = service.findAll(companyId, page, size);
        pageInfo.getList();
        request.setAttribute("page", pageInfo);
        return "/system/dept/dept-list";
    }

    @RequestMapping(path = "/toUpdate",name = "前往部门修改页面")
    public String toUpdate(String id){
        Dept dept = service.findById(id);
        //这里的companyId都是测试数据
        //实际开发中应该在会话范围中的user对象中获取
        List<Dept> deptList = service.findAll(companyId);
        //进行数据回显
        request.setAttribute("deptList", deptList);
        request.setAttribute("dept", dept);
        return "/system/dept/dept-update";
    }

    @RequestMapping(path = "/toAdd",name = "前往部门添加页面")
    public String toAdd(){
        List<Dept> deptList = service.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "/system/dept/dept-add";
    }

    @RequestMapping(path = "/edit",name = "添加或修改部门信息")
    public String edit(Dept dept){
        //进行判断带有id的为修改过程，不带id的为添加过程
        if (StringUtils.isEmpty(dept.getId())) {
            //添加的部门没有企业id和企业名称，通常在登录后会在会话范围中的user中可以获取，
            //测试数据
            dept.setCompanyId(companyId);
            dept.setCompanyName(companyName);

            //添加一个部门
            service.save(dept);
        }else {
            //修改部门信息
//            System.out.println(dept);
            service.update(dept);
        }

        return "redirect:/system/dept/list.do";
    }

    @RequestMapping(path = "/delete",name = "删除一个部门")
    public String delete(String id){
        service.delete(id);
        return "redirect: /system/dept/list.do";
    }
}
