package cn.itcast.web.controller.cargo;


import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cargo/packing")
public class PackingController extends BaseController {

    @Reference
    private PackingService packingService;
    @Reference
    private ExportService exportService;


    //分页显示
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        PackingExample example = new PackingExample();
        PackingExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo info = packingService.findAll(example, page, size);
        request.setAttribute("page", info);
        return "/cargo/packing/packing-list";
    }

    //跳转添加页面
    @RequestMapping(value = "/toPacking")
    public String toPacking(String id) {
        request.setAttribute("id", id);
        return "/cargo/packing/packing-toPacking";
    }


    //报运单列表
    @RequestMapping(value = "/exportList")
    public String exportList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {

        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andStateEqualTo(2L);
        criteria.andCompanyIdEqualTo(companyId);

        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/packing/packing-exportList";
    }


    //进入修改页面进行数据回显
    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        Packing packing = packingService.findById(id);
        request.setAttribute("packing", packing);

        String exportIds = packing.getExportIds();
        String[] exportId = exportIds.split(",");

        List<Export> list = new ArrayList<>();
        for (String s : exportId) {
            Export export = exportService.findById(s);
            export.setState(2);
            list.add(export);

        }

        PageInfo<Export> pageInfo = new PageInfo<>(list);
        request.setAttribute("page", pageInfo);

        return "cargo/packing/packing-update";



    }


    //保存
    @RequestMapping("/edit")
    public String edit(Packing packing) {
        packing.setCompanyId(companyId);
        packing.setCompanyName(companyName);
        if (StringUtils.isEmpty(packing.getPackingListId())) {
            packingService.save(packing);
        } else {
            packingService.update(packing);
        }
        return "redirect:/cargo/packing/list.do";
    }

    //删除
    @RequestMapping(value = "/delete")
    public String delete(String id) {
        packingService.delete(id);
        return "redirect:/cargo/packing/list.do";
    }


    //装货单提交
    @RequestMapping(value = "/submit")
    public String submit(String id) {
        Packing packing = new Packing();
        packing.setPackingListId(id);
        packing.setState(1L);
        packingService.update(packing);
        return "redirect:/cargo/packing/list.do";
    }

    //装货单取消
    @RequestMapping(value = "/cancel")
    public String cancel(String id) {
        Packing packing = new Packing();
        packing.setPackingListId(id);
        packing.setState(0L);
        packingService.update(packing);
        return "redirect:/cargo/packing/list.do";
    }

}
