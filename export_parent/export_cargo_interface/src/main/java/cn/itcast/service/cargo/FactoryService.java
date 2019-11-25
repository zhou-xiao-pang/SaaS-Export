package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Factory;
import cn.itcast.domain.cargo.FactoryExample;

import java.util.List;

public interface FactoryService {
    /**
     * 根据id查找所有的厂家列表，用于下拉列表，不用分页
     * @param
     * @return
     */
    List<Factory> findAll(FactoryExample example);
}
