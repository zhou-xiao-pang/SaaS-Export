package cn.itcast.service.company.test;

import cn.itcast.service.company.CompanyService;
import cn.itcast.service.dept.DeptService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CompanyServiceTest {

    @Autowired
    private CompanyService service;

    @Autowired
    private DeptService deptService;

    @Test
    public void findAll() {
//        PageResult result = service.findAllPage(1,10);
//        System.out.println("all = " + all);
//        System.out.println("result = " + result);
        PageInfo list = deptService.findAll("100", 1, 5);
        System.out.println("all = " + list.getList());
    }
}