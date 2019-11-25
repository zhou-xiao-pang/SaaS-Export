package cn.itcast.web.shiro;

import cn.itcast.common.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.util.Objects;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 密码比较
     * @param token
     * @param info 安全数据（user） 用户的数据库密码
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //将AuthenticationToken转换成我们用的UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        //取出用户输入的密码
        String userPassword = String.valueOf(upToken.getPassword());
        //取出用户名当盐
        String userName = upToken.getUsername();
        //对用户输入的密码进行加密
        String md5Password = Encrypt.md5(userPassword, userName);
        //取出安全数据中的user中的密码（根据用户传来的用户名查出的密码，既数据库中对应此用户名的密码）
        String dbPassword= String.valueOf(info.getCredentials());

        //返回两个密码比较的结果
        return Objects.equals(dbPassword, md5Password);
    }
}
