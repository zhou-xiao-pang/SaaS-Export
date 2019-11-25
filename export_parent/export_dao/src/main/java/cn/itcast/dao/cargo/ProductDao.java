package cn.itcast.dao.cargo;

import cn.itcast.domain.cargo.Product;
import cn.itcast.domain.cargo.ProductExample;
import cn.itcast.domain.vo.ContractProductVo;

import java.util.List;

public interface ProductDao {

	//删除
    int deleteByPrimaryKey(String id);

	//保存
    int insertSelective(Product record);

	//条件查询
    List<Product> selectByExample(ProductExample example);

	//id查询
    Product selectByPrimaryKey(String id);

	//更新
    int updateByPrimaryKeySelective(Product record);

    //根据合同id删除
    int deleteByContractId(String id);


    /**
     * 根据船期查找vo
     * @param inputDate
     * @return
     */
    List<ContractProductVo> findContractProductVoByShipTime(String inputDate);
}