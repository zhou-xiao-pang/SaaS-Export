package cn.itcast.dao.company;

import cn.itcast.common.common.entity.PageResult;
import cn.itcast.domain.company.Company;
import java.util.List;

public interface CompanyDao {
   /**
    * 查询所有企业的记录
    * @return
    */
   PageResult findAllPage();

    void save(Company company);

    //根据id查找一个企业的记录
    Company findCompanyById(String id);

    //修改企业信息
    void update(Company company);

    //删除一条企业记录
    void delete(String id);

    //分页查询的记录
    List<Company> findAll();

    Long findCountOfAll();

}
