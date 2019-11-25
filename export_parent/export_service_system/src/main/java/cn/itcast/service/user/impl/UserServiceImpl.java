package cn.itcast.service.user.impl;

import cn.itcast.common.common.utils.Encrypt;
import cn.itcast.dao.user.UserDao;
import cn.itcast.domain.user.User;
import cn.itcast.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
   /* @Override
    public PageInfo<User> findAll(String companyId, String deptId, int page, int size) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.findAll(companyId, deptId);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        return userPageInfo;
    }*/

//    @Override
//    public List<Role> findUserRole(String id) {
//        if (id == null)
//            throw new IllegalArgumentException("参数错误");
//        return userDao.findUserRole(id);
//
//    }

    @Override
    public void changeRole(String id, String[] roleIds) {
        //删除用户的所有角色
        userDao.deleteUserRole(id);
        System.out.println("roleIds = " + roleIds);
        //添加新的所有角色
        for (String roleId : roleIds) {
            System.out.println("roleId = " + roleId);
            userDao.insertUserRole(roleId, id);
        }
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(Encrypt.md5(user.getPassword(),user.getEmail()));
//        System.out.println("业务层中的user = " + user);
        userDao.save(user);
    }

    @Override
    public PageInfo<User> findAll(String companyId, int page, int size) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.findAll(companyId);
        PageInfo<User> userPageInfo = new PageInfo<>(list);
        return userPageInfo;
    }

    @Override
    public User findByEmail(String email) {
        if (email == null)
            throw new IllegalArgumentException("参数错误");
        return userDao.findByEmail(email);
    }

    @Override
    public User login(String email, String password) {
        password = Encrypt.md5(password, email);
        return userDao.login(email, password);
    }


    @Override
    public void updatePassword(User user) {
        String password = Encrypt.md5(user.getPassword(),user.getEmail());
        userDao.updatePassword(password,user.getId());
    }

    @Override
    public PageInfo findAllCompanyRoot(int page,int size) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.findAllCompanyRoot();
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public User findByOpenid(String openid) {
        return userDao.findByOpenid(openid);
    }

}
