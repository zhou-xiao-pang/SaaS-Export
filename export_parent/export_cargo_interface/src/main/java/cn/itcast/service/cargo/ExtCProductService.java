package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.ExtCproduct;
import cn.itcast.domain.cargo.ExtCproductExample;
import com.github.pagehelper.PageInfo;

public interface ExtCProductService {

    /**
     * 分页查询
     * @param example
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(ExtCproductExample example, int page, int size);

    /**
     * 添加一个附件
     * @param extCproduct
     * @return
     */
    int save(ExtCproduct extCproduct);

    /**
     * 修改一个附件的信息
     * @param extCproduct
     * @return
     */
    int update(ExtCproduct extCproduct);

    /**
     * 根据id查找一个附件实体
     * @param id
     * @return
     */
    ExtCproduct findById(String id);

    /**
     * 删除一个附件
     * @param id
     * @return
     */
    int delete(String id);
}
