package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChangeDetail;

import java.util.List;

/**
* @author Administrator
* @description 针对表【storage_change_detail(入库变更详情表)】的数据库操作Service
* @createDate 2025-04-24 18:08:54
*/
public interface StorageChangeDetailService extends IService<StorageChangeDetail> {

    void createOrUpdare(List<StorageChangeDetail> data,String id);


    List<StorageChangeDetail> selectByScId(String scId);
}
