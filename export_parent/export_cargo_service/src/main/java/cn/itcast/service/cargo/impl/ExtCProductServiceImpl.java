package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.dao.cargo.ProductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import cn.itcast.service.cargo.ExtCProductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExtCProductServiceImpl implements ExtCProductService {
    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }
    @Override
    public int save(ExtCproduct extCproduct) {
        //添加一个附件，与添加货物雷同
        //设置id
        extCproduct.setId(UUID.randomUUID().toString());
        //计算总金额amount
        double amount = 0.0d;
        if (extCproduct.getPrice() != null && extCproduct.getCnumber() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //为附件的金额赋值
        extCproduct.setAmount(amount);
        //根据contractId查找合同实体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //设置合同上的总金额
        contract.setTotalAmount(contract.getTotalAmount() + extCproduct.getAmount());
        //设置合同上的附件数量extNum
        contract.setExtNum(contract.getExtNum() + extCproduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);

        //添加附件
        return extCproductDao.insertSelective(extCproduct);
    }

    @Override
    public int update(ExtCproduct extCproduct) {
        //更新附件信息
        //计算总金额
        double amount = 0.0d;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //设置附件的金额
        extCproduct.setAmount(amount);
        //获取原来的附件实体类
        ExtCproduct oldExtC = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //根据contractId查找一个合同实体
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //修改合同上的总金额
        System.out.println("contractServiceImpl = " + contract);
        contract.setTotalAmount(contract.getTotalAmount() - oldExtC.getAmount() + extCproduct.getAmount());
        //修改合同上的附件数量
        contract.setExtNum(contract.getExtNum() - oldExtC.getCnumber() + extCproduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);

        //更新附件
        return extCproductDao.updateByPrimaryKeySelective(extCproduct);
    }

    @Override
    public ExtCproduct findById(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数异常");
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public int delete(String id) {
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //修改合同总金额
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        //修改附件数量
        contract.setExtNum(contract.getExtNum() - extCproduct.getCnumber());
        //更新合同
        contractDao.updateByPrimaryKeySelective(contract);
        //删除附件
        return extCproductDao.deleteByPrimaryKey(id);
    }
}
