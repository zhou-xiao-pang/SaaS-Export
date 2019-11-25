package cn.itcast.web.controller.sysLog;

import cn.itcast.domain.sysLog.SysLog;
import cn.itcast.domain.user.User;
import cn.itcast.service.sysLog.SysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class SaveLog {
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    @Around(value = "execution(* cn.itcast.web.controller.*.*.*(..))")
    public Object log(ProceedingJoinPoint pjp) throws Throwable{
        User user = (User)session.getAttribute("loginUser");
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RequestMapping requestMapping= method.getAnnotation(RequestMapping.class);
        if (user != null && requestMapping != null) {
            SysLog sysLog = new SysLog();
            sysLog.setCompanyId(user.getCompanyId());//设置企业id
            sysLog.setCompanyName(user.getCompanyName());//设置企业名称
            sysLog.setUserName(user.getUserName());//设置使用者的id
            sysLog.setMethod(method.getName());//设置方法名
            sysLog.setIp(request.getLocalAddr());//设置id地址
            sysLog.setAction(requestMapping.name());//设置执行的动作
            sysLog.setTime(new Date());//设置执行时间

            System.out.println("执行到添加");
            sysLogService.save(sysLog);
        }
        return pjp.proceed();
    }
}
