package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.dao.cargo.ProductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import cn.itcast.domain.cargo.Product;
import cn.itcast.service.cargo.ContractService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public PageInfo<Contract> findAll(ContractExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Contract> list = contractDao.selectByExample(example);
        return new PageInfo<Contract>(list);
    }
    @Override
    public Contract findById(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public int save(Contract contract) {
        contract.setId(UUID.randomUUID().toString());
        contract.setCreateTime(new Date());
        contract.setUpdateTime(new Date());
        contract.setState(0);
        return contractDao.insertSelective(contract);
    }

    @Override
    public int update(Contract contract) {
        return contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public int delete(String id) {
        //删除合同，要删除合同下的货物和附件
        //根据合同id删除附件
        extCproductDao.deleteByContractId(id);
        //根据合同id删除货物
        productDao.deleteByContractId(id);
        //删除合同
        return contractDao.deleteByPrimaryKey(id);
    }
}
