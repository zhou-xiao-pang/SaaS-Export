package cn.itcast.web.controller.cargo;

import cn.itcast.common.common.utils.UploadUtil;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.service.cargo.ProductService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ProductController extends BaseController {

    @Reference
    private ProductService productService;

    @Reference
    private FactoryService factoryService;

    @Reference
    private ContractService contractService;

    @RequestMapping("/list")
    public String list(String contractId,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        //货物分页查询
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        PageInfo<Product> pageInfo = productService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        //厂家列表查询
        FactoryExample example1 = new FactoryExample();
        FactoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example1);
        request.setAttribute("factoryList", factoryList);
        request.setAttribute("contractId",contractId);
        return "/cargo/product/product-list";
    }

    @RequestMapping("/edit")
    public String edit(String contractId, Product product, MultipartFile productPhoto) throws IOException {
        //上传照片
        if (!productPhoto.isEmpty() && productPhoto != null) {
            UploadUtil uploadUtil = new UploadUtil();
            String path = uploadUtil.upload(productPhoto.getBytes());
            product.setProductImage(path);
        }

        product.setCompanyId(companyId);
        product.setCompanyName(companyName);
//        System.out.println("contractId = " + contractId);
//        System.out.println("product = " + product);
        if (StringUtils.isEmpty(product.getId())) {
            //添加一个新的货物
            productService.save(product);
        } else {
            //更新货物信息
            productService.update(product);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //数据回显
        Product product = productService.findById(id);
        request.setAttribute("contractProduct", product);
        FactoryExample example1 = new FactoryExample();
        FactoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(example1);
        request.setAttribute("factoryList", factoryList);
        return "/cargo/product/product-update";
    }

    @RequestMapping("/delete")
    public String delete(String id ,String contractId){
        //删除操作
        productService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    //    /cargo/contractProduct/toImport.do?contractId
    @RequestMapping(value = "toImport")
    public String toImport(String contractId) {

        request.setAttribute("contractId", contractId);
        return "/cargo/product/product-import";
    }

    @RequestMapping(value = "/import")
    public String Import(String contractId,MultipartFile file) throws IOException {
        //上传excel表导出表中的数据存入数据库,记得设置合同id
        //接收传来的流化的数据
        //创建工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream()); //2007 xml xss--->2007及以上版本
        //创建页
        Sheet sheet = wb.getSheetAt(0);
        //创建一个object数组来接收一行的数据
        Object[] objs = new Object[10];
        for (int i = 1; i <=sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            //循环objs数组将每个单元格的数据放入数组中
            for (int j = 1; j < 10; j++) {
                Cell cell = row.getCell(j);
                objs[j] = getCellValue(cell);
                System.out.println("objs["+j+"] = " + objs[j]);
            }
            Product product = new Product(objs,companyId,companyName);
            product.setContractId(contractId);
            productService.save(product);
        }

        return "redirect:/cargo/contract/list.do?contractId=" + contractId;
    }









    //将单元格转换成对应的类型
    public static Object getCellValue(Cell cell){
        Object o = new Object();

        switch (cell.getCellType()){
            case STRING:
                o = cell.getStringCellValue();
                break;
            case BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    o = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                }else{
                    o = cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                break;
        }

        return o;
    }
}
