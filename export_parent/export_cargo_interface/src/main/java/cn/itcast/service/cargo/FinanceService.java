package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Finance;
import cn.itcast.domain.cargo.FinanceExample;
import com.github.pagehelper.PageInfo;

public interface FinanceService {
    /**
     * 分页查询，财务报运单
     * @return
     */
    PageInfo<Finance> findAll(FinanceExample example,int page, int size);

    /**
     * 根据id查询一个财务报运单
     * @param id
     * @return
     */
    Finance findById(String id);


    /**
     * 修改一个财务报运单
     * @param finance
     */
    void update(Finance finance);

    /**
     * 删除一个财务报运单
     * @param id
     */
    void delete(String id);

    /**
     * 添加一个财务报运单
     * @param finance
     */
    void save(Finance finance);
}
