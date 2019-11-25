package cn.itcast.web.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        if (e instanceof UnauthorizedException){
//            return new ModelAndView("unauthorized");
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("unauthorized");
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView();
            //   /WEB-INF/pages/error.jsp
            modelAndView.setViewName("error");
            modelAndView.addObject("errorMsg", "对不起，我错了，页面已经美化了，但是能力一般！");
            return modelAndView;
        }
    }
}
