package cn.itcast.web.controller.cargo;

import cn.itcast.common.common.utils.MailUtil;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import cn.itcast.domain.cargo.Shipping;
import cn.itcast.domain.cargo.ShippingExample;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.service.cargo.ShippingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/cargo/shipping")
public class ShippingController extends BaseController {

    @Reference
    private ShippingService shippingService;

    @Reference
    private PackingService packingService;

    //分页显示
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        ShippingExample example = new ShippingExample();
        ShippingExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo info = shippingService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/shipping/shipping-list";
    }

    //跳转添加页面
    @RequestMapping("/toAdd")
    public String toAdd(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        PackingExample example = new PackingExample();
        PackingExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(1L);
        PageInfo info = packingService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "cargo/shipping/shipping-add";
    }

    //更新
    @RequestMapping("/toUpdate")
    public String toUpdate(String id,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Shipping shipping = shippingService.findById(id);
        request.setAttribute("shipping", shipping);

        ArrayList<Packing> list = new ArrayList<>();
        Packing packing = packingService.findById(id);
        list.add(packing);
        PageInfo<Packing> info = new PageInfo<>(list);
        request.setAttribute("page",info);
        return "cargo/shipping/shipping-update";
    }

    //新增保存
    @RequestMapping(value = "/edit")
    public String edit(Shipping shipping) throws Exception {
        shipping.setCompanyId(companyId);
        shipping.setCompanyName(companyName);
        shippingService.save(shipping);

        MailUtil.sendMsg(loginUser.getEmail(), "装箱委托", "委托：装运港"+shipping.getLoadingPort()+
                "   卸货港："+shipping.getDischargePort()+"  货主："+shipping.getShipper());

        return "redirect:/cargo/shipping/list.do";
    }


    //修改保存
    @RequestMapping(value = "/update")
    public String update(Shipping shipping) {
        shipping.setCompanyId(companyId);
        shipping.setCompanyName(companyName);
        shippingService.update(shipping);
        return "redirect:/cargo/shipping/list.do";
    }

    //删除
    @RequestMapping(value = "/delete")
    public String delete(String id) {
        shippingService.delete(id);
        return "redirect:/cargo/shipping/list.do";
    }

    //提交
    @RequestMapping(value = "/submit")
    public String submit(String id) {
        Shipping shipping = new Shipping();
        shipping.setShippingOrderId(id);
        shipping.setState(1);
        shippingService.update(shipping);
        return "redirect:/cargo/shipping/list.do";
    }

    //取消
    @RequestMapping(value = "/cancel")
    public String cancel(String id) {
        Shipping shipping = new Shipping();
        shipping.setShippingOrderId(id);
        shipping.setState(0);
        shippingService.update(shipping);
        return "redirect:/cargo/shipping/list.do";
    }

}





















































