package cn.itcast.web.controller.company;

import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {

    @Reference
    private CompanyService companyService;

    @RequestMapping("/apply")
    public @ResponseBody String apply(Company company) {

        System.out.println("进入了controller");


        try {
            company.setState(0);
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            return "2";
        }


    }

}
