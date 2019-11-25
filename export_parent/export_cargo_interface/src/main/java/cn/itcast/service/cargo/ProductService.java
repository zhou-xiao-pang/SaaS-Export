package cn.itcast.service.cargo;

import cn.itcast.domain.cargo.Product;
import cn.itcast.domain.cargo.ProductExample;
import cn.itcast.domain.vo.ContractProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    /**
     * 分页查询 contractId is Required
     * @param example
     * @param page
     * @param size
     * @return
     */
    PageInfo<Product> findAll(ProductExample example, int page, int size);

    /**
     * 根据id 查找货物
     * @param id
     * @return
     */
    Product findById(String id);

    /**
     * 添加一个货物
     * @param product
     * @return
     */
    int save(Product product);

    /**
     * 根据id修改货物的信息
     * @param product
     * @return
     */
    int update(Product product);

    /**
     * 根据id删除一个货物
     * @param id
     * @return
     */
    int delete(String id);

    /**
     * 根据船期查找vo
     * @param inputDate
     * @return
     */
    List<ContractProductVo> findContractProductVoByShipTime(String inputDate);
}
