package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.domain.vo.ExportProductVo;
import cn.itcast.domain.vo.ExportResult;
import cn.itcast.domain.vo.ExportVo;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ExportService exportService;

    @Reference
    private ContractService contractService;

    @Reference
    private ExportProductService exportProductService;

    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andStateEqualTo(1);
        PageInfo<Contract> pageInfo = contractService.findAll(contractExample, page, size);
        request.setAttribute("page", pageInfo);
        return "/cargo/export/export-contractList";
    }

    @RequestMapping("/toExport")
    public String toExport(String id){
        request.setAttribute("id", id);
        System.out.println("id = " + id);
        return "/cargo/export/export-toExport";
    }

//    /cargo/export/list.do
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria exampleCriteria = exportExample.createCriteria();
        exampleCriteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "/cargo/export/export-list";
    }

    @RequestMapping("/toView")
    public String toView(String id){
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        return "/cargo/export/export-view";
    }

    @RequestMapping("/edit")
    public String edit(Export export) {
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);
        if (StringUtils.isEmpty(export.getId())) {
            exportService.save(export);
        }else {
            exportService.update(export);
        }
        return "redirect:list.do";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        //eps
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(export.getId());
        List<ExportProduct> eps = exportProductService.findAll(exportProductExample);
        request.setAttribute("eps", eps);
        return "/cargo/export/export-update";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        Export export = exportService.findById(id);
        export.setState(1);
        exportService.update(export);
        return "redirect:list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        Export export = exportService.findById(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        exportService.delete(id);
        return "redirect:/cargo/export/list.do";
    }

//    /cargo/export/exportE.do?id="+id;
    @RequestMapping("/exportE")
    public String exportE(String id){
        //掉海关的接口进行电子报运，以下是海关需要的数据
//{
//    exportId:"",
//    inputDate:"",
//    shipmentPort:"",
//    destinationPort:"",
//    transportMode:"",
//    priceCondition:"",
//    boxNums:"",
//    grossWeights:"",
//    measurements:"",
//    products:[
//            {
//            exportProductId:"",
//            factoryId:"",
//            productNo:"",
//            packingUnit:"",
//            cnumber:"",
//            boxNum:"",
//            grossWeight:"",
//            netWeight:"",
//            sizeLength:"",
//            sizeWidth:"",
//            sizeHeight:"",
//            exPrice:"",
//            price:"",
//            tax:""
//            },
//            {
//            exportProductId:"",
//            factoryId:"",
//            productNo:"",
//            packingUnit:"",
//            cnumber:"",
//            boxNum:"",
//            grossWeight:"",
//            netWeight:"",
//            sizeLength:"",
//            sizeWidth:"",
//            sizeHeight:"",
//            exPrice:"",
//            price:"",
//            tax:""
//        }
//	]
//}
        //通过报运单id查询报运单实体
        Export exportById = exportService.findById(id);
        //创建报运单vo实体
        ExportVo exportVo = new ExportVo();
        //复制报运单的数据到vo中
        BeanUtils.copyProperties(exportById, exportVo);
        //为exportVo设置exportId
        exportVo.setExportId(id);
        //根据报运单id查找报运单下的商品
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> exportProducts = exportProductService.findAll(exportProductExample);
        List<ExportProductVo> epVos = new ArrayList<>();

        //循环exportProducts
        for (ExportProduct exportProduct : exportProducts) {
            //创建epVo实体，添加到epVOs中
            ExportProductVo exportProductVo = new ExportProductVo();
            //复制商品数据到epVo中
            BeanUtils.copyProperties(exportProduct, exportProductVo);
            //为epVo设置exportId和exportProductId
            exportProductVo.setExportId(id);
            exportProductVo.setExportProductId(exportProduct.getId());
            //添加到epVos中
            epVos.add(exportProductVo);
        }

        //为exportVo设置products
        exportVo.setProducts(epVos);

        //调用海关接口传数据
        WebClient wc = WebClient.create("http://localhost:8085/ws/export/user");
        wc.post(exportVo);

        //第二次调用海关接口传入id查询，返回一个实体类
        wc = WebClient.create("http://localhost:8085/ws/export/user/"+id);

        ExportResult exportResult = wc.get(ExportResult.class);
        //根据返回的结果修改数据库
        exportService.updateExportE(exportResult);
        return "redirect:list.do";
    }
}
