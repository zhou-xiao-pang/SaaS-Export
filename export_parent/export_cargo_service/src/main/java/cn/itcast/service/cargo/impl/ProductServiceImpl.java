package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.ContractDao;
import cn.itcast.dao.cargo.ExtCproductDao;
import cn.itcast.dao.cargo.ProductDao;
import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.Product;
import cn.itcast.domain.cargo.ProductExample;
import cn.itcast.domain.vo.ContractProductVo;
import cn.itcast.service.cargo.ProductService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ExtCproductDao extCproductDao;
    @Override
    public PageInfo<Product> findAll(ProductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Product> list = productDao.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public Product findById(String id) {
        if (id == null)
            throw new IllegalArgumentException("参数错误");
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ContractProductVo> findContractProductVoByShipTime(String inputDate) {
        return productDao.findContractProductVoByShipTime(inputDate);
    }

    @Override
    public int save(Product product) {
//        System.out.println("productServiceImpl= " + product);
        //添加一个新的货物
        //设置货物的id
        product.setId(UUID.randomUUID().toString());
        //计算amount
        double amount = 0.0d;
        if (product.getCnumber()!=null && product.getPrice()!=null){
            amount = product.getCnumber() * product.getPrice();
        }
        product.setAmount(amount);
        //1、修改货物对应的合同上的货物数量和金额
        Contract contract = contractDao.selectByPrimaryKey(product.getContractId());
        System.out.println("contractServiceImpl = " + contract);
        //        //新增货物金额在原基础上增加
        contract.setTotalAmount(contract.getTotalAmount() + product.getAmount());
        //新增货物数量在原基础上增加
        contract.setProNum(contract.getProNum() + product.getCnumber());
        //2、amount为总金额（数量*单价）在数据库中新增货物记录
        //更新合同
        System.out.println("contractServiceImpl = " + contract);
        contractDao.updateByPrimaryKeySelective(contract);
        return productDao.insertSelective(product);
    }

    @Override
    public int update(Product product) {
        //修改货物
        double amount = 0.0d;
        if (product.getCnumber()!=null && product.getPrice()!=null){
            amount = product.getCnumber() * product.getPrice();
        }
        product.setAmount(amount);
        //根据id获取原来的货物信息
        Product oldProduct = productDao.selectByPrimaryKey(product.getId());
        //根据contractId获取对应的合同信息
        Contract contract = contractDao.selectByPrimaryKey(product.getContractId());
        //修改合同上的货物数量 = 合同上的货物数目-原来这种货物数目+现在这种货物的数目
        contract.setProNum(contract.getProNum() - oldProduct.getCnumber() + product.getCnumber());
        //修改合同上的金额 =合同上的货物金额-原来这种货物金额+现在这种货物的金额
        contract.setTotalAmount(contract.getTotalAmount() - oldProduct.getAmount() + product.getAmount());
        //更新和同信息
        contractDao.updateByPrimaryKeySelective(contract);
        //更新货物信息
        return  productDao.updateByPrimaryKeySelective(product);
    }

    @Override
    public int delete(String id) {
        //删除一个货物
        //删除货物修改合同上的数目和金额
        //根据id查找对应的货物对应的合同id
        Product product = productDao.selectByPrimaryKey(id);
        Contract contract = contractDao.selectByPrimaryKey(product.getContractId());
        //删除附件信息
        List<ExtCproduct> extCproducts = product.getExtCproducts();
        for (ExtCproduct extCproduct : extCproducts) {
            //遍历删除货物下的附件及修改合同中附件的信息
            contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
            contract.setExtNum(contract.getExtNum() - extCproduct.getCnumber());
            extCproductDao.deleteByPrimaryKey(extCproduct.getId());
        }

        contract.setProNum(contract.getProNum() - product.getCnumber());
        contract.setTotalAmount(contract.getTotalAmount() - product.getAmount());
        //更新合同信息
        contractDao.updateByPrimaryKeySelective(contract);


        //删除货物记录
        return productDao.deleteByPrimaryKey(id);
    }
}
