package cn.itcast.web.controller.cargo;

import cn.itcast.common.common.utils.BeanMapUtils;
import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import cn.itcast.domain.cargo.ProductExample;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.ProductService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cargo/export")
public class JasperController extends BaseController {
    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    @RequestMapping("/exportPdf")
    public void exportPdf(String id) throws JRException, IOException {
        //根据id 查找export实体，使用工具类转换为map集合
        Export export = exportService.findById(id);
        Map<String, Object> map = BeanMapUtils.beanToMap(export);

        //根据id查找exportProduct实体
        ExportProductExample exportProductExample = new ExportProductExample();
        exportProductExample.createCriteria().andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(exportProductExample);
        //获取jasper文件的路径
        String path = session.getServletContext().getRealPath("\\") + "\\jasper\\export.jasper";
        System.out.println("path = " + path);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //通过jasperManager管理器读取jasper文件 创建jrprint
        JasperPrint jasperPrint = JasperFillManager.fillReport(path, map, dataSource);
        //通过jasperExportManager管理器输出pdf文件
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }
}
