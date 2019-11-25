package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.domain.cargo.ShippingExample;
import cn.itcast.service.cargo.InvoiceService;
import cn.itcast.service.cargo.ShippingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cargo/invoice")
public class InvoiceController  extends BaseController {

    @Reference
    private InvoiceService invoiceService;

    @RequestMapping("/toView")
    public String toView(String invoiceId){
//        System.out.println("invoiceId = " + invoiceId);
        Invoice invoice = invoiceService.findById(invoiceId);
        request.setAttribute("invoice",invoice);
        return "/cargo/invoice/invoice-view";
    }
    @Reference
    private ShippingService shippingService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        InvoiceExample example=new InvoiceExample();
        InvoiceExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = invoiceService.findAll(example, page, size);
        request.setAttribute("page",pageInfo);
        return "cargo/invoice/invoice-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        ShippingExample example = new ShippingExample();
        ShippingExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = shippingService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/invoice/invoice-add";
    }

    @RequestMapping("/save")
    public String edit(Invoice invoice) {
        invoice.setCompanyId(companyId);
        invoice.setCompanyName(companyName);
        invoice.setCreatBy(loginUser.getUserName());
        invoice.setCreatDept(loginUser.getDeptName());
        invoiceService.save(invoice);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/update")
    public String update(Invoice invoice){
        invoiceService.update(invoice);
        return "redirect:/cargo/invoice/list.do";
    }



    @RequestMapping("/delete")
    public String delete(String id) {
        invoiceService.delete(id);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(id);
        invoice.setState(1);
        invoiceService.update(invoice);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        Invoice invoice=new Invoice();
        invoice.setInvoiceId(id);
        invoice.setState(0);
        invoiceService.update(invoice);
        return "redirect:/cargo/invoice/list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        Invoice invoice = invoiceService.findById(id);
        request.setAttribute("invoice",invoice);
        return "cargo/invoice/invoice-update";
    }
}
