package cn.itcast.service.role.impl;

import cn.itcast.dao.role.RoleDao;
import cn.itcast.domain.role.Role;
import cn.itcast.service.role.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public List<Role> findUserRole(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return roleDao.findUserRole(id);
    }

    @Override
    public int delete(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return roleDao.delete(id);
    }

    @Override
    public int update(Role role) {
        if (role == null)
            throw new IllegalArgumentException("参数错误");
        return roleDao.update(role);
    }

    @Override
    public Role findById(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return roleDao.findById(id);
    }

    @Override
    public int save(Role role) {
        if (role == null)
            throw new IllegalArgumentException("参数错误");
        role.setId(UUID.randomUUID().toString());
        return roleDao.save(role);
    }

    @Override
    public PageInfo<Role> findAll(String company, int page, int size) {
        PageHelper.startPage(page, size);
        List<Role> list = roleDao.findAll(company);
        return new PageInfo<>(list);
    }
}
