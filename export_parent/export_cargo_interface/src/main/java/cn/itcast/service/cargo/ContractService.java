package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Contract;
import cn.itcast.domain.cargo.ContractExample;
import com.github.pagehelper.PageInfo;

public interface ContractService {

    /**
     * 合同的分页查询
     * @param example 条件任意，CompanyId is required
     * @param page
     * @param size
     * @return
     */
    PageInfo<Contract> findAll(ContractExample example, int page, int size);

    /**
     * 根据id查询一个合同
     * @param id
     * @return
     */
    Contract findById(String id);

    /**
     * 添加一个合同
     * @param contract
     * @return
     */
    int save(Contract contract);

    /**
     * 修改一个合同的内容
     * @param contract
     * @return
     */
    int update(Contract contract);

    /**
     * 根据id删除一个合同
     * @param id
     * @return
     */
    int delete(String id);
}
