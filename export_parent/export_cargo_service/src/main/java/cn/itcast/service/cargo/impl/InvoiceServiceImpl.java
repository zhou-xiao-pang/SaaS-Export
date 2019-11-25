package cn.itcast.service.cargo.impl;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import cn.itcast.domain.cargo.Packing;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExportDao;
import cn.itcast.dao.cargo.InvoiceDao;
import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.service.cargo.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private PackingDao packingDao;
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private ContractDao contractDao;
    @Override
    public PageInfo findAll(InvoiceExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Invoice> list = invoiceDao.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void save(Invoice invoice) {
        double amount=0;
        invoice.setCreatDate(new Date());
        invoice.setState(0);
        Packing packingList = packingDao.selectByPrimaryKey(invoice.getInvoiceId());
        packingList.setState(2L);
        if (packingList!=null) {
            String exportIds = packingList.getExportIds();
            String[] exports = exportIds.split(",");
            for (String exportId : exports) {
                Export export = exportDao.selectByPrimaryKey(exportId);
                String contractIds = export.getContractIds();
                String[] contracts = contractIds.split(",");
                for (String contractId : contracts) {
                    Contract contract = contractDao.selectByPrimaryKey(contractId);
                    Double totalAmount = contract.getTotalAmount();
                    amount += totalAmount;
                }
            }
        }
        invoice.setAmount(amount);
        invoiceDao.insertSelective(invoice);
    }

    @Override
    public void delete(String id) {
        invoiceDao.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Invoice invoice) {
        invoiceDao.updateByPrimaryKeySelective(invoice);
    }

    @Override
    public Invoice findById(String id) {
        Invoice invoice = invoiceDao.selectByPrimaryKey(id);
        return invoice;
    }
}
