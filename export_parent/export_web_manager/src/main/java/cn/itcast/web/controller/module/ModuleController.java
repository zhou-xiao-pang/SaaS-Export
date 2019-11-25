package cn.itcast.web.controller.module;

import cn.itcast.domain.module.Module;
import cn.itcast.service.module.ModuleService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private ModuleService moduleService;


    @RequestMapping(path = "/list",name = "查看所有的模块列表")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        PageInfo pageInfo = moduleService.findAll(page,size);
        request.setAttribute("page", pageInfo);
        return "/system/module/module-list";
    }

    @RequestMapping(path = "/toAdd",name = "前往添加模块的页面")
    public String toAdd(){
        List<Module> list = moduleService.findAll();
        request.setAttribute("menus", list);
        return "system/module/module-add";
    }

    @RequestMapping(path = "/edit",name = "添加或修改一个模块")
    public String edit(Module module) {
        if (StringUtils.isEmpty(module.getId())) {
            moduleService.save(module);
        }else {
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }

    @RequestMapping(path = "/toUpdate",name = "前往修改模块页面")
    public String toUpdate(String id){
        //回显
       Module module =  moduleService.findById(id);
       request.setAttribute("module",module);
        List<Module> list = moduleService.findAll();
        request.setAttribute("menus", list);
        return "/system/module/module-update";
    }

    @RequestMapping(path = "/delete",name = "删除一个模块")
    public String delete(String id) {
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }

}
