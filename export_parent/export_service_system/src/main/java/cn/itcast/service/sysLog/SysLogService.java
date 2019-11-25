package cn.itcast.service.sysLog;

import cn.itcast.domain.sysLog.SysLog;
import com.github.pagehelper.PageInfo;

public interface SysLogService {
    //分页查询所有的企业日志
    PageInfo findAll(String companyId, int page , int size);

    //添加一个日志
    int save(SysLog sysLog);
}
