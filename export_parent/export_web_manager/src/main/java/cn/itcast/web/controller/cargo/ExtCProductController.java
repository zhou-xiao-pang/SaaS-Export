package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;
import cn.itcast.service.cargo.ExtCProductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/cargo/extCproduct")
@Controller
public class ExtCProductController extends BaseController {
    @Reference
    private ExtCProductService extCProductService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int page , @RequestParam(defaultValue = "5") int size) {
        //页面厂家列表
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        ExtCproductExample example = new ExtCproductExample();
        ExtCproductExample.Criteria exampleCriteria = example.createCriteria();
        //与货物id相同的附件
        exampleCriteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo = extCProductService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "/cargo/extc/extc-list";
    }

    @RequestMapping(value = "/edit",name = "添加或修改")
    public String edit(ExtCproduct extCproduct){
        System.out.println("extCproduct = " + extCproduct);
        if (StringUtils.isEmpty(extCproduct.getId())) {
            extCproduct.setCompanyId(companyId);
            extCproduct.setCompanyName(companyName);
            extCProductService.save(extCproduct);
            return "redirect:/cargo/extCproduct/list.do?contract=" + extCproduct.getContractId() +
                    "&contractProductId=" + extCproduct.getContractProductId();
        }else{
            extCProductService.update(extCproduct);
            return "redirect:/cargo/extCproduct/list.do?contract=" + extCproduct.getContractId() +
                    "&contractProductId=" + extCproduct.getContractProductId();
        }
    }

    @RequestMapping(value = "/toUpdate",name = "前往修改页面")
    public String toUpdate(String id){
        //数据回显
        //页面厂家列表
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryExampleCriteria = factoryExample.createCriteria();
        factoryExampleCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        ExtCproduct extCproduct = extCProductService.findById(id);
        request.setAttribute("extCproduct", extCproduct);
        request.setAttribute("contractId", extCproduct.getContractId());
        request.setAttribute("contractProductId",extCproduct.getContractProductId());
        return "cargo/extc/extc-update";
    }

    @RequestMapping(value = "/delete",name = "删除附件操作")
    public String delete(String id) {
        ExtCproduct extCproduct = extCProductService.findById(id);
        extCProductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contract=" + extCproduct.getContractId() +
                "&contractProductId=" + extCproduct.getContractProductId();
    }

}
