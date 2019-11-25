package cn.itcast.service.stat.impl;

import cn.itcast.dao.stat.StatDao;
import cn.itcast.service.stat.StatService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatDao statDao;

    @Override
    public List<Map> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    @Override
    public List<Map> getSellData(String companyId) {
        return statDao.getSellData(companyId);
    }

    @Override
    public List<Map> getOnlineData(String companyId) {
        return statDao.getOnlineData(companyId);
    }

    public List<Map> getMarketData(String companyId) {
        return statDao.getMarketData(companyId);
    }

    public List<Map> getVisitData(String companyId) {
        return statDao.getVisitData(companyId);
    }
}
