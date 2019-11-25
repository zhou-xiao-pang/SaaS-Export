package cn.itcast.web.controller;


import cn.itcast.common.common.utils.HttpUtils;
import cn.itcast.domain.module.Module;
import cn.itcast.domain.user.User;
import cn.itcast.service.module.ModuleService;
import cn.itcast.service.user.UserService;
import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController extends BaseController{

    public static final String APPID = "wx3bdb1192c22883f3";
    public static final String APPSECRET = "db9d6b88821df403e5ff11742e799105";


    @Autowired
    private UserService userService;


    @Autowired
    private ModuleService moduleService;

	@RequestMapping("/login1")
	public String login(String email,String password) {
        try {
            //创建subject实体类
            Subject subject = SecurityUtils.getSubject();
            //通过邮箱密码创建upToken
            UsernamePasswordToken upToken = new UsernamePasswordToken(email, password);

            //subject.login(upToken)
            subject.login(upToken);//login进入两个方法 1、身份认证  2、密码比较

            //通过subject获取安全数据
            User user = (User) subject.getPrincipal();
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
        /*if (email == null && password == null) {
            request.setAttribute("error","请输入用户名或密码！");
            return "forward:login.jsp";
        }else {
            User user = userService.login(email, password);
            if (user == null) {
                request.setAttribute("error", "用户名或密码错误");
            return "forward:login.jsp";
            }
            //根据user来动态的生成列表
            List<Module> modules = moduleService.findUserModules(user);
            session.setAttribute("modules",modules);
            session.setAttribute("loginUser", user);
        }*/
    }

   /* //通过微信code登录
    *//**
     * 微信授权登录
     *//*
    @RequestMapping("/wxLogin")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // backUrl 需要遵循微信官方的定义，微信的接口只能用 https 来访问
        // 所以我这里是直接把整个项目打包成 jar 包，然后扔到自己的服务器上
        String backUrl = "http://note.java.itcast.cn/weixinlogin";
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + HttpUtils.APPID + "&redirect_uri="
                + URLEncoder.encode(backUrl) + "&response_type=code" + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
    }
    @RequestMapping("/weixinlogin")
    protected void weixinlogin(HttpServletRequest request, HttpServletResponse response)
            throws ClientProtocolException, IOException, ServletException {
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + HttpUtils.APPID + "&secret="
                + HttpUtils.APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        Map map = HttpUtils.sendGet(url);
        Object openid = map.get("openid");
        Object token = map.get("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
                + "&lang=zh_CN";
        Map map1 = HttpUtils.sendGet(infoUrl);

        if (map1 != null) {
            // 这里是把授权成功后，获取到的东西放到 info 里面，前端可以通过 EL 表达式直接获取相关信息
            request.setAttribute("info", map1);
            // 这里是授权成功返回的页面
            request.getRequestDispatcher("/success.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/fail.jsp").forward(request, response);
        }
    }*/
    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
