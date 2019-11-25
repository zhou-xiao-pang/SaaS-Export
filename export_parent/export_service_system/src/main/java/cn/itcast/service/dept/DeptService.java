package cn.itcast.service.dept;

import cn.itcast.domain.dept.Dept;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DeptService {
    //查找所有的部門
    PageInfo findAll(String companyId, int page, int size);

    //查询该企业的所有部门，用于部门添加中的父部门别表使用
    List<Dept> findAll(String companyId);

    //按id查找一个部门
    Dept findById(String id);

    //添加一个部门
    void save(Dept dept);

    //删除一个部门，并删除其下的所有子部门（方法一：在数据库中可以实现）
    //方法二：当删除该部门时语句 delete from pe_dept where id = #{id} or parent_id = #{id}
    void delete(String id);

    //修改部门信息
    void update(Dept dept);
}
