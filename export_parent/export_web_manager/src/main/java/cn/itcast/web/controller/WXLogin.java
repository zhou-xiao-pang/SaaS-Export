package cn.itcast.web.controller;

import cn.itcast.common.common.utils.HttpUtils;
import cn.itcast.domain.module.Module;
import cn.itcast.domain.user.User;
import cn.itcast.service.module.ModuleService;
import cn.itcast.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Controller
public class WXLogin extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;

    /*@RequestMapping("/weixinlogin")
    public void login() throws UnsupportedEncodingException {
        System.out.println("================>>");
        String url = "https://open.weixin.qq.com/connect/qrconnect?";
        url += "appid=wx3bdb1192c22883f3";


        url += "&redirect_uri=" + URLEncoder.encode("http://note.java.itcast.cn/wxLogin/callBackLogin.do", "UTF-8");  //此处和微信会调用的域名相同
        url += "&response_type=code&scope=snsapi_login";
        url += "&state=" + request.getSession().getId() + "#wechat_redirect";
//redirect_uri；指定回调路径
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    @RequestMapping(value = "/login")
    public String callBackLogin(String code) {
        System.out.println("callBackLogin....");
// return "redirect:../loginSuccess.jsp";
        System.out.println("code=" + code);

        try {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        url += "appid=wx3bdb1192c22883f3";
        url += "&secret=db9d6b88821df403e5ff11742e799105";


        url += "&code=" + code + "&grant_type=authorization_code";


        Map map = HttpUtils.sendGet(url);
        String at = (String)map.get("access_token");//获取微信开放平台票据号
        String openId = (String)map.get("openid");

        System.out.println("openId = " + openId);
        System.out.println("at = " + at);

        User userByOpenid = userService.findByOpenid(openId);
        //创建subject实体类
        Subject subject = SecurityUtils.getSubject();
        //通过邮箱密码创建upToken
        UsernamePasswordToken upToken = new UsernamePasswordToken(userByOpenid.getEmail(), userByOpenid.getPassword());


        //subject.login(upToken)
        subject.login(upToken);//login进入两个方法 1、身份认证  2、密码比较

        //通过subject获取安全数据
        User user = (User) subject.getPrincipal();
        System.out.println("userByOpenid = " + userByOpenid);


        if (user != null) {
            //放入会话范围中
            session.setAttribute("loginUser", user);
            //通过user对象查找菜单
            List<Module> modules = moduleService.findUserModules(user);
            session.setAttribute("modules", modules);
            //进行登录
            return "home/main";
        } else {

            request.setAttribute("error", "用户名或密码错误");
            return "forward:login.jsp";
        }

    } catch (Exception e) {
        //如果出现异常提示用户名或密码错误
        request.setAttribute("error", "用户名或密码错误异常");
        return "forward:login.jsp";
    }

    }
}
