package cn.itcast.test.CompanyDaoTest;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CompanyDaoTest {

    @Autowired
    private CompanyDao dao;

    @Test
    public void findAll() {
//        List<Company> all = dao.findAll();
//        System.out.println("all = " + all);
//        List<Company> list = dao.findAll(null,null);
//        Long countOfAll = dao.findCountOfAll();
//        System.out.println("countOfAll = " + countOfAll);
//        System.out.println("list = " + list);
    }
}
