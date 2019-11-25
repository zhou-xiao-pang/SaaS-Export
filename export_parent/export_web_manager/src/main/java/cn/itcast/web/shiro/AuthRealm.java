package cn.itcast.web.shiro;

import cn.itcast.domain.module.Module;
import cn.itcast.domain.user.User;
import cn.itcast.service.module.ModuleService;
import cn.itcast.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AuthRealm extends AuthorizingRealm {
    @Autowired
    ModuleService moduleService;

    @Autowired
    UserService userService;
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //取出安全数据中的user
        User user = (User) principalCollection.getPrimaryPrincipal();
        //通过user获取到他的菜单列表
        List<Module> modules = moduleService.findUserModules(user);
        //构建set集合
        Set<String> set = new  HashSet<String>();
        //将列表放入集合中
        for (Module module : modules) {
            set.add(module.getName());
        }

        //2、返回AuthorizationInfo里有一个 set集合stringPermissions
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //将authenticationToken强转为UsernamePasswordToken类型
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        //通过upToken取出用户名
        String email = upToken.getUsername();
        //通过用户名查找出对应的实体，放入安全数据
        User user = userService.findByEmail(email);
        //创建AuthenticationInfo对象返回，需要的三个参数
        // Object principal 安全数据，User
        // Object credentials 用户的数据库密码
        // String realmName 可以随意取名，但是我们一般用类名
        AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
        return info;
    }
}
