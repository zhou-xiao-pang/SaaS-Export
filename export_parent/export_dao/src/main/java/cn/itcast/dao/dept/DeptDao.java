package cn.itcast.dao.dept;

import cn.itcast.domain.dept.Dept;

import java.util.List;

public interface DeptDao {

    //查找该企业的所有的部門
    List<Dept> findAll(String companyId);

    //按id查找一个部门
    Dept findById(String id);

    //添加一个部门
    void save(Dept dept);

    //根据id删除部门和其下的子部门
    void delete(String id);

    //修改部门信息
    void update(Dept dept);
}
