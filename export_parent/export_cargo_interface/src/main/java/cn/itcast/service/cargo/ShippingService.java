package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Shipping;
import cn.itcast.domain.cargo.ShippingExample;
import com.github.pagehelper.PageInfo;

public interface ShippingService {

    //分页查询
    PageInfo findAll(ShippingExample example, int page, int size);

    //保存合同信息
    void save(Shipping shipping);

    //通过id进行查询
    Shipping findById(String id);

    //更新合同信息
    void update(Shipping shipping);

    //通过id进行删除
    void delete(String id);

}
