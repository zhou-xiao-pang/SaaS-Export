package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.FinanceDao;
import cn.itcast.dao.dept.DeptDao;
import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import cn.itcast.domain.dept.Dept;
import cn.itcast.service.cargo.FinanceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class FinanceServiceImpl implements FinanceService {
    @Autowired
    private FinanceDao financeDao;

    @Autowired
    private DeptDao deptDao;

    @Override
    public PageInfo<Finance> findAll(FinanceExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Finance> list = financeDao.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Finance findById(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return financeDao.selectByPrimaryKey(id);
    }

    @Override
    public void update(Finance finance) {
        if (finance == null)
            throw new IllegalArgumentException("参数错误");
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Override
    public void delete(String id) {
        if (id == null )
            throw new IllegalArgumentException("参数错误");
        financeDao.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Finance finance) {
        if (finance == null)
            throw new IllegalArgumentException("参数错误");
        finance.setFinanceId(UUID.randomUUID().toString());
        Dept dept = deptDao.findById(finance.getCreateDept());
        finance.setDeptName(dept.getDeptName());
        financeDao.insertSelective(finance);
    }
}
