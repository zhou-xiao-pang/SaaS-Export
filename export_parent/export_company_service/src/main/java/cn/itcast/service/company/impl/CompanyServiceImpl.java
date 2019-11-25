package cn.itcast.service.company.impl;

import cn.itcast.dao.company.CompanyDao;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Override
    public void delete(String id) {
        if (id==null)
            throw new IllegalArgumentException("参数错误");
        companyDao.delete(id);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public Company findCompanyById(String id) {
        if (id ==null)
            throw new IllegalArgumentException("参数错误");
        return companyDao.findCompanyById(id);
    }

//    @Override
//    public PageResult findAllPage(Integer page,Integer size) {
//        Long count = (Long) dao.findCountOfAll();
//        System.out.println("count = " + count);
//        List<Company> list = dao.findAll((page-1)*size, size);
//        System.out.println("list = " + list);
//        PageResult pageResult = new PageResult(count, list, page, size);
//
//        return pageResult;
//    }

    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    @Override
    public void save(Company company) {
        if (company == null)
            throw new IllegalArgumentException("参数异常");
        company.setId(UUID.randomUUID().toString());
        companyDao.save(company);
    }
}
