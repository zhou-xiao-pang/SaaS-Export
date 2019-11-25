package cn.itcast.web.controller.company;


import cn.itcast.common.common.utils.MailUtil;
import cn.itcast.domain.company.Company;
import cn.itcast.domain.user.User;
import cn.itcast.service.company.CompanyService;
import cn.itcast.service.user.UserService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService service;
    @Autowired
    private UserService userService;

//    @RequestMapping("/list")
//    public String findAll(String page,String size,HttpServletRequest request){
//        int page1 = Integer.parseInt(page);
//        int size1 = Integer.parseInt(size);
//        PageResult result = service.findAllPage(page1, size1);
////        System.out.println("all = " + list);
//        System.out.println(result.getRows());
//        request.setAttribute("result", result.getRows());
//        return "company/company-list";
//    }

    @RequestMapping(path = "/list",name = "查看所有的企业列表")
    public String findAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<Company> list = service.findAll();
        request.setAttribute("page", new PageInfo<>(list));
        return "/company/company-list";
    }


    @RequestMapping(path = "/toAdd",name = "前往添加页面")
    public String toAdd(){
        return "/company/company-add";
    }

    @RequestMapping(path = "/edit",name = "添加或修改一个企业")
    public String edit(Company company){
        if (StringUtils.isEmpty(company.getId())) {
//            System.out.println("company = " + company);
            service.save(company);
        } else {
            service.update(company);
        }

        return "redirect:/company/list.do";
    }

    @RequestMapping(path = "/toUpdate", name = "前往修改页面")
    public String toUpdate(String id) {
//        System.out.println("111");
        Company company = service.findCompanyById(id);
//        System.out.println(company);
        request.setAttribute("company", company);
//        System.out.println("回显company = " + company);
        return "company/company-update";
    }

    @RequestMapping(path = "/delete",name = "删除一个企业 ")
    public String delete(String id){
        service.delete(id);
//        System.out.println(id);
        return "redirect:/company/list.do";
    }
    @RequestMapping("/toCompanyRootAdd")
    public String toCompanyRootAdd(ModelMap modelMap){
        List<Company> companyList = service.findAll();
        modelMap.addAttribute("companyList", companyList);
        return "company/companyRoot-add";
    }

    @RequestMapping("/companyRootEdit")
    public String edit(User user) throws Exception {
        if (StringUtils.isEmpty(user.getId())) {
            String password = user.getPassword();
            userService.save(user);
            MailUtil.sendMsg(user.getEmail(),"欢迎加入SaaSExport","SaaS-Export登陆地址是：http://localhost:8080/login.do  你的用户名是："+user.getEmail()+"密码是："+password);
        }else {
            userService.update(user);
        }
        return "redirect:/company/list.do";
    }

    @RequestMapping("/companyRootList")
    public String companyRootList(ModelMap modelMap,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "5") int size){
        PageInfo pageInfo = userService.findAllCompanyRoot(page,size);
        modelMap.addAttribute("page", pageInfo);
        return "company/companyRoot-list";
    }

    @RequestMapping("/toCompanyRootUpdate")
    public String toCompanyRootUpdate(String id,ModelMap modelMap){
        User user = userService.findById(id);
        modelMap.addAttribute("user", user);
        List<Company> companyList = service.findAll();
        modelMap.addAttribute("companyList", companyList);
        return "company/companyRoot-add";
    }
    @RequestMapping("/deleteRoot")
    public String deleteRoot(String id){
        userService.delete(id);
        return "redirect:/company/companyRootList.do";
    }
}
