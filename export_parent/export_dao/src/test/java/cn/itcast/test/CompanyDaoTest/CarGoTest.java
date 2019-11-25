package cn.itcast.test.CompanyDaoTest;

import cn.itcast.dao.cargo.FactoryDao;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CarGoTest {
    @Autowired
    private FactoryDao factoryDao;

    @Test
    public void factoryDaoTest(){
//        添加测试
        /*Factory factory = new Factory();
        factory.setAddress("测试使用地址");
        factory.setId(UUID.randomUUID().toString());
        factory.setFactoryName("测试使用的工厂名");
        factory.setCreateTime(new Date());
        factory.setUpdateTime(new Date());
        factoryDao.insertSelective(factory);*/


//        查找测试
        FactoryExample example = new FactoryExample();
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo("4028817a39b2b37f0139b46268c40025");
        List<Factory> factories = factoryDao.selectByExample(example);
        for (Factory factory : factories) {
            System.out.println("factory = " + factory);
        }
    }
}
