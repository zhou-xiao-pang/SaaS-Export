package cn.itcast.web.controller.stat;

import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.xml.internal.rngom.parse.host.Base;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
//'/stat/factoryCharts.do'
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;

    @RequestMapping(value = "/factoryCharts")
    public @ResponseBody List<Map> factoryCharts(){
        List<Map> factoryData = statService.getFactoryData(companyId);
        System.out.println("factoryData = " + factoryData);
        return factoryData;
    }

    @RequestMapping(value = "/toCharts")
    public String toChars(String chartsType){
        return "/stat/stat-" + chartsType;
    }

    @RequestMapping(value = "/getOnlineData")
    public @ResponseBody List<Map> onlineCharts(){
        List<Map> onlineCharts = statService.getOnlineData(companyId);
        System.out.println("onlineCharts = " + onlineCharts);
        return onlineCharts;
    }

    @RequestMapping(value = "/getSellData")
    public @ResponseBody List<Map> sellCharts(){
        List<Map> sellCharts = statService.getSellData(companyId);
        System.out.println("sellCharts = " + sellCharts);
        return sellCharts;
    }

    @RequestMapping(value = "/getMarketData")
    public @ResponseBody List<Map> getMarketData(){
        return statService.getMarketData(companyId);
    }

    @RequestMapping(value = "/getVisitData")
    public @ResponseBody List<Map> getVisitData(){
        return statService.getVisitData(companyId);
    }
}
