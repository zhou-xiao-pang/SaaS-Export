package cn.itcast.service.cargo.impl;

import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.dao.cargo.ShippingDao;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.Shipping;
import cn.itcast.domain.cargo.ShippingExample;
import cn.itcast.service.cargo.ShippingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingDao shippingDao;

    @Autowired
    private PackingDao packingDao;


    //分页
    @Override
    public PageInfo findAll(ShippingExample example, int page, int size) {
        //设置参数
        PageHelper.startPage(page, size);
        //查询全部
        List<Shipping> shippingList = shippingDao.selectByExample(example);
        //设置返回值
        return new PageInfo(shippingList);
    }

    //保存
    @Override
    public void save(Shipping shipping) {
        //获取Packing实体
        Packing packing = packingDao.selectByPrimaryKey(shipping.getShippingOrderId());
        //设置packing已委托
        packing.setState(2L);
        //更新packing状态
        packingDao.updateByPrimaryKeySelective(packing);
        //设置shipping为草稿
        shipping.setState(0);
        //更新shipping
        shippingDao.insertSelective(shipping);


    }

    //按ID查询
    @Override
    public Shipping findById(String id) {
        return shippingDao.selectByPrimaryKey(id);
    }

    //更新
    @Override
    public void update(Shipping shipping) {
        shippingDao.updateByPrimaryKeySelective(shipping);
    }

    //删除
    @Override
    public void delete(String id) {
        //根据id实例
        Packing packing = packingDao.selectByPrimaryKey(id);
        //设置状态
        packing.setState(1L);
        //更新packing
        packingDao.updateByPrimaryKeySelective(packing);
        //删除
        shippingDao.deleteByPrimaryKey(id);

    }
}
