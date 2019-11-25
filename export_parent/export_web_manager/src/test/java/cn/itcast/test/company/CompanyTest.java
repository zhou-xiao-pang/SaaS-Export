package cn.itcast.test.company;

import cn.itcast.domain.company.Company;
import cn.itcast.domain.module.Module;
import cn.itcast.domain.role.Role;
import cn.itcast.domain.user.User;
import cn.itcast.service.company.CompanyService;
import cn.itcast.service.module.ModuleService;
import cn.itcast.service.role.RoleService;
import cn.itcast.service.user.UserService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CompanyTest {

    @Autowired
    private CompanyService service;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ModuleService moduleService;

    @Test
    public void save(){
        Company company = new Company();
        company.setId("1");
        service.save(company);
    }

    @Test
    public void findOne(){
        User user = userService.login("lw@export.com", "72a7dc98f2ce9f5312a7267d88c965d1");
        List<Module> moduleList = moduleService.findUserModules(user);
        System.out.println("moduleList = " + moduleList);
    }
}
