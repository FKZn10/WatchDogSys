package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChange;

/**
 * @author Administrator
 * @description 针对表【storage_change(入库变更表)】的数据库操作Service
 * @createDate 2025-04-24 18:08:54
 */
public interface StorageChangeService extends IService<StorageChange> {


    BaseResponse createOrUpdateStorageChange(StorageChangeRequest request);

    BaseResponse getStorageChangeList(StorageChangeRequest request);
}
