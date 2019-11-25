package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Invoice;
import cn.itcast.domain.cargo.InvoiceExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public interface InvoiceService {
    PageInfo findAll(InvoiceExample example, int page, int size);

    void save(Invoice invoice);

    void delete(String id);

    void update(Invoice invoice);

    Invoice findById(String id);
}
