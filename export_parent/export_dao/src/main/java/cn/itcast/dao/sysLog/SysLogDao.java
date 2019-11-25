package cn.itcast.dao.sysLog;


import cn.itcast.domain.sysLog.SysLog;
import java.util.List;

public interface SysLogDao {

    //分页查询所有的企业日志
    List<SysLog> findAll(String companyId);

    //添加一条日志
    int save(SysLog sysLog);
}
