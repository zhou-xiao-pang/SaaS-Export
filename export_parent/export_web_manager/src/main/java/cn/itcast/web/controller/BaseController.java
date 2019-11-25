package cn.itcast.web.controller;

import cn.itcast.domain.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected String companyId;
    protected String companyName;
    protected User loginUser;


    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        this.request = request;
        this.response = response;
        this.session = session;
//        this.companyId = "1";
//        this.companyName = "传智播客教育股份有限公司";
        loginUser = (User)session.getAttribute("loginUser");
        if(loginUser != null) {
            this.companyId = loginUser.getCompanyId();
            this.companyName=loginUser.getCompanyName();
        }
    }
}
