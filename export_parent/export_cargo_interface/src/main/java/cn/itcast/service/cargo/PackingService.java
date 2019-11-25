package cn.itcast.service.cargo;


import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import com.github.pagehelper.PageInfo;

public interface PackingService {

    //分页查询
    PageInfo findAll(PackingExample example, int page, int size);

    //保存合同信息
    void save(Packing packing);

    //通过id进行查询
    Packing findById(String id);

    //更新合同信息
    void update(Packing packing);

    //通过id进行删除
    void delete(String id);

}
