package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.domain.vo.ExportResult;
import com.github.pagehelper.PageInfo;


public interface ExportService {

    Export findById(String id);


    void save(Export export);

    void update(Export export);

    void delete(String id);

	PageInfo findAll(ExportExample example, int page, int size);

    //根据海关传来的结果修改数据库
    void updateExportE(ExportResult exportResult);
}
