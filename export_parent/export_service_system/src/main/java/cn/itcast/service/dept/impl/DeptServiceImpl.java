package cn.itcast.service.dept.impl;

import cn.itcast.dao.dept.DeptDao;
import cn.itcast.domain.dept.Dept;
import cn.itcast.service.dept.DeptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao deptDao;

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public void delete(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        deptDao.delete(id);
    }

    @Override
    public void save(Dept dept) {
        //添加一个id
        dept.setId(UUID.randomUUID().toString());
        if  (StringUtils.isEmpty(dept.getParent().getId())) {
            dept.getParent().setId(null);
        }
        deptDao.save(dept);

    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        PageHelper.startPage(page, size);
        List<Dept> list = deptDao.findAll(companyId);
        PageInfo<Dept> deptPageInfo = new PageInfo<>(list);
        return deptPageInfo;
    }

    @Override
    //查询该企业的所有部门，用于部门添加中的父部门别表使用
    public List<Dept> findAll(String companyId) {
        return deptDao.findAll(companyId);
    }
}
