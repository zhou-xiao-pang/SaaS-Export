package cn.itcast.service.company;

import cn.itcast.domain.company.Company;

import java.util.List;

public interface CompanyService {
    /**
     *查询所有的企业记录
     * @return
     *
     */
//    PageResult findAllPage(Integer page,Integer size);


    //查询所有的企业
    List<Company> findAll();

    //添加一个企业
    void save(Company company);

    //根据id查找一个企业记录
    Company findCompanyById(String id);

    //修改企业信息
    void update(Company company);

    //删除一条企业记录
    void delete(String id);
}
