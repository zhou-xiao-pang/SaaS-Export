package cn.itcast.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {

    //查找厂家销售数据
    List<Map> getFactoryData(String companyId);

    //查找商品销售数据
    List<Map> getSellData(String companyId);

    //查找在线人数，压力测试
    List<Map> getOnlineData(String companyId);

    //市场价格统计
    List<Map> getMarketData(String companyId);

    //用户登录情况
    List<Map> getVisitData(String companyId);
}
