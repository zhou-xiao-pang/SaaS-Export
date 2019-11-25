package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.domain.dept.Dept;
import cn.itcast.service.cargo.FinanceService;
import cn.itcast.service.cargo.InvoiceService;
import cn.itcast.service.dept.DeptService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {
    @Reference
    private FinanceService financeService;

    @Reference
    private InvoiceService invoiceService;

    @Autowired
    private DeptService deptService;

    /*@RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "5") int size){
        FinanceExample financeExample = new FinanceExample();
        financeExample.createCriteria().andCompanyIdEqualTo(companyId);
        PageInfo<Finance> pageInfo = financeService.findAll(financeExample, page, size);
        List<Dept> depts = deptService.findAll(companyId);
        System.out.println("list = " + pageInfo.getList());
        request.setAttribute("page", pageInfo);
        return "/cargo/finance/finance-list";
    }*/

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1")int page, @RequestParam(defaultValue = "5") int size){
        InvoiceExample invoiceExample = new InvoiceExample();
        invoiceExample.createCriteria().andStateEqualTo(1);
        PageInfo pageInfo = invoiceService.findAll(invoiceExample, page, size);
        request.setAttribute("page", pageInfo);
        return "/cargo/invoice/invoice-checked";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList", list);
        return "/cargo/finance/finance-add";
    }

    @RequestMapping("/invoiceList")
    public String invoice(String invoiceIds){
        return "cargo/invoice/invoice-list";
    }

    @RequestMapping("/edit")
    public String edit(Finance finance){
        //设置企业id和企业名称
        finance.setCompanyId(companyId);
        finance.setCompanyName(companyName);
//        System.out.println("finance = " + finance);
        if (StringUtils.isEmpty(finance.getFinanceId())) {
            financeService.save(finance);
        }else {
            financeService.update(finance);
        }
        return "redirect:/cargo/finance/list.do";
    }

    @RequestMapping("/toView")
    public String toView(String financeId){
//        System.out.println("financeId = " + financeId);
        Finance finance = financeService.findById(financeId);
//        System.out.println("finance = " + finance);
        request.setAttribute("finance",finance);
        return "/cargo/finance/finance-view";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String financeId){
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList", list);

        Finance finance = financeService.findById(financeId);
        request.setAttribute("finance",finance);
        return "/cargo/finance/finance-update";
    }

    @RequestMapping("/toExport")
    public String toExport(String invoiceId){
        request.setAttribute("id", invoiceId);
        return "/cargo/finance/finance-add";
    }

}
