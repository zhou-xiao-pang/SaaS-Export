package cn.itcast.service.cargo.impl;


import cn.itcast.dao.cargo.ExportDao;
import cn.itcast.dao.cargo.PackingDao;
import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportExample;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.PackingExample;
import cn.itcast.service.cargo.PackingService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//装箱单
@Service
public class PackingServiceImpl implements PackingService {

    @Autowired
    private PackingDao packingDao;
    @Autowired
    private ExportDao exportDao;

    //分页查询
    public PageInfo findAll(PackingExample example, int page, int size) {

        //设置分页参数
        PageHelper.startPage(page,size);
        //查询全部
        List<Packing> list = packingDao.selectByExample(example);
        //构造返回值
        return new PageInfo(list);
    }



    //根据id查询0
    public Packing findById(String id) {
        return packingDao.selectByPrimaryKey(id);
    }

    //保存
    public void save(Packing packing) {
        //1.设置id 唯一
        packing.setPackingListId(UUID.randomUUID().toString());
        //2.设置状态为 0
        packing.setState(0L);
        //3.查询报运单
        //根据传入的多个报运单id进行分离
        String[] exportIds = packing.getExportIds().split(",");
        ExportExample example = new ExportExample();
        ExportExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(Arrays.asList(exportIds)); //遍历集合为集合
        //查询出多个货运单
        List<Export> exports = exportDao.selectByExample(example);
        //4.拼接报运单号
        String exportNos = "";
        for (Export export : exports) {
            exportNos += export.getCustomerContract() + " ";
            //5.修改报运单状态
            export.setState(3);
            exportDao.updateByPrimaryKeySelective(export);
        }
        //6.设置装箱单
        packing.setExportNos(exportNos);
        //7.保存
        packingDao.insertSelective(packing);
    }

    //更新
    public void update(Packing packing) {
        packingDao.updateByPrimaryKeySelective(packing);
    }

    //删除
    public void delete(String id) {
        //1.根据装箱单查询报运单
        Packing packing = packingDao.selectByPrimaryKey(id);
        //2.查询多个报运单id存入数组中
        String[] ids = packing.getExportIds().split(",");

        for (String exportId : ids) {
            Export export = exportDao.selectByPrimaryKey(exportId);
            export.setState(2);
            exportDao.updateByPrimaryKeySelective(export);
        }
        packingDao.deleteByPrimaryKey(id);
    }


}
