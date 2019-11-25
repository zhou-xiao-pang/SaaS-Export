package cn.itcast.web.controller.cargo;

import cn.itcast.common.common.utils.DownloadUtil;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.vo.ContractProductVo;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ProductService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;

    @Reference
    private ProductService productService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        if (loginUser.getDegree() == 1) {
            criteria.andCreateDeptEqualTo(loginUser.getDeptId());
        } else if (loginUser.getDegree() == 4) {
            criteria.andCreateByEqualTo(loginUser.getId());
        }
        PageInfo<Contract> pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "/cargo/contract/contract-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "/cargo/contract/contract-add";
    }

    @RequestMapping("/edit")
    public String edit(Contract contract) {
        contract.setCompanyId(companyId);
        contract.setCompanyName(companyName);
        if (StringUtils.isEmpty(contract.getId())) {
            //创建合同时添加创建人,和创建部门
            contract.setCreateBy(loginUser.getId());
            contract.setCreateDept(loginUser.getDeptId());
            contractService.save(contract);
            return "redirect:/cargo/contract/list.do";
        } else {
            contractService.update(contract);
            return "redirect:/cargo/contract/list.do";
        }
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Contract contract = contractService.findById(id);
        request.setAttribute("contract", contract);
        return "/cargo/contract/contract-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        contractService.delete(id);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping("/submit")
    public String submit(String id) {

        //根据id查找这个合同将状态码改为1
        Contract contract = contractService.findById(id);
        contract.setState(1);
        //更新这个合同的信息
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }


    @RequestMapping("/cancel")
    public String cancel(String id){
        //根据id查找这个合同将状态码该为2
        Contract contract = contractService.findById(id);
        contract.setState(2);
        contractService.update(contract);
        return "redirect:/cargo/contract/list.do";
    }

    @RequestMapping("/toView")
    public String toView(String id){
        //数据回显
        Contract contract = contractService.findById(id);

        request.setAttribute("contract", contract);

        return "/cargo/contract/contract-view";
    }

    @RequestMapping("/print")
    public String print(){
        return "cargo/print/contract-print";
    }

//    /cargo/contract/printExcel.do?inputDate=2015-01
    @RequestMapping("/printExcel")
    public void printExcel(String inputDate) throws IOException {
        //通过inputDate查找出vo数据
        List<ContractProductVo> vos = productService.findContractProductVoByShipTime(inputDate);
        ////2、创建EXCEL工作薄
        XSSFWorkbook wb = new XSSFWorkbook();
//        /创建页
        XSSFSheet sheet = wb.createSheet();

        //设置列宽
        sheet.setColumnWidth(1, 26*256);
        sheet.setColumnWidth(2, 12*256);
        sheet.setColumnWidth(3, 30*256);
        sheet.setColumnWidth(4, 12*256);
        sheet.setColumnWidth(5, 15*256);
        sheet.setColumnWidth(6, 10*256);
        sheet.setColumnWidth(7, 10*256);
        sheet.setColumnWidth(8, 10*256);

        //合并单元格第一行，从第一列到第八列
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 1, 8);
        sheet.addMergedRegion(cellAddresses);

        //大标题
        Row row = sheet.createRow(0);
        //设置行高
        row.setHeightInPoints(36);
        //创建第二个单元格
        Cell cell = row.createCell(1);
        //为这个单元格添加样式
        cell.setCellStyle(bigTitle(wb));
        cell.setCellValue(inputDate.replaceAll("-", "年")+"月份出货表");  //2015年01月份出货表

        //小标题
        row = sheet.createRow(1);
        row.setHeightInPoints(26);

        String[] strings = new String[]{"", "客户", "订单号", "货号", "数量", "工厂", "工厂交期", "船期", "贸易条款"};
        //循环添加小标题
        for (int i = 1; i < strings.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(strings[i]);
            //为小标题添加样式
            cell.setCellStyle(title(wb));
        }

        //建立索引，从第三行开始
        int index = 2;
        for (ContractProductVo vo : vos) {
            //创建行
            row = sheet.createRow(index);

            cell = row.createCell(1);
            cell.setCellValue(vo.getCustomName());
            cell.setCellStyle(text(wb));

            cell = row.createCell(2);
            cell.setCellValue(vo.getContractNo());
            cell.setCellStyle(text(wb));

            cell = row.createCell(3);
            cell.setCellValue(vo.getProductNo());
            cell.setCellStyle(text(wb));

            cell = row.createCell(4);
            cell.setCellValue(vo.getCnumber());
            cell.setCellStyle(text(wb));

            cell = row.createCell(5);
            cell.setCellValue(vo.getFactoryName());
            cell.setCellStyle(text(wb));

            cell = row.createCell(6);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
            cell.setCellStyle(text(wb));

            cell = row.createCell(7);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
            cell.setCellStyle(text(wb));

            cell = row.createCell(8);
            cell.setCellValue(vo.getTradeTerms());
            cell.setCellStyle(text(wb));
            index++;
        }

        //下载excel表
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        new DownloadUtil().download(outputStream, response, "出货表.xlsx");
    }


    @RequestMapping(value = "/printByTemplate")
    public void printByTemplate(String inputDate)throws IOException{
        //根据inputDate查找对应的vo数据
        List<ContractProductVo> vos = productService.findContractProductVoByShipTime(inputDate);

        //打开工作簿
        Workbook wb = new XSSFWorkbook("D:\\Java学习\\stage3\\SaaS-Export-day10\\03-资料\\excel资源\\tOUTPRODUCT.xlsx");
        //获取第一页
        Sheet sheet = wb.getSheetAt(0);

        //大标题
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(1);
        cell.setCellValue(inputDate.replaceAll("-", "年") + "月份出货表");

        //小标题

        //内容
        //循环获取样式
        row = sheet.getRow(2);
        CellStyle[] css = new CellStyle[9];
        for (int i = 1; i < css.length; i++) {
            cell = row.getCell(i);
            CellStyle cellStyle = cell.getCellStyle();
            css[i] = cellStyle;
        }

        //添加内容
        int index = 2;
        for (ContractProductVo vo : vos) {
            //获取对应的行
            row = sheet.createRow(index);
            //获取对应的单元格
            cell = row.createCell(1);
            cell.setCellValue(vo.getCustomName());
            cell.setCellStyle(css[1]);


            cell = row.createCell(2);
            cell.setCellValue(vo.getContractNo());
            cell.setCellStyle(css[2]);

            cell = row.createCell(3);
            cell.setCellValue(vo.getProductNo());
            cell.setCellStyle(css[3]);

            cell = row.createCell(4);
            cell.setCellValue(vo.getCnumber());
            cell.setCellStyle(css[4]);

            cell = row.createCell(5);
            cell.setCellValue(vo.getFactoryName());
            cell.setCellStyle(css[5]);

            cell = row.createCell(6);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDeliveryPeriod()));
            cell.setCellStyle(css[6]);

            cell = row.createCell(7);
            cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getShipTime()));
            cell.setCellStyle(css[7]);

            cell = row.createCell(8);
            cell.setCellValue(vo.getTradeTerms());
            cell.setCellStyle(css[8]);
            index++;
        }

        //下载excel表
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        wb.write(outputStream);
        new DownloadUtil().download(outputStream,response,"出货表.xlsx");
    }

    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }

}
