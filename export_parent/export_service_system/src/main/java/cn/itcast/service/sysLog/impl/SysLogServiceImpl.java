package cn.itcast.service.sysLog.impl;

import cn.itcast.dao.sysLog.SysLogDao;
import cn.itcast.domain.sysLog.SysLog;
import cn.itcast.service.sysLog.SysLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogDao sysLogDao;
    @Override
    public PageInfo findAll(String companyId, int page, int size) {
        PageHelper.startPage(page, size);
        List<SysLog> list = sysLogDao.findAll(companyId);
        return new PageInfo<>(list);
    }

    @Override
    public int save(SysLog sysLog) {
        if (sysLog==null)
            throw new IllegalArgumentException("参数错误");
        sysLog.setId(UUID.randomUUID().toString());
        return sysLogDao.save(sysLog);
    }
}
