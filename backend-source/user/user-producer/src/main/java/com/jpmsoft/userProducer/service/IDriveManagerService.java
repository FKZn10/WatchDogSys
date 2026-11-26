package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.entity.DriveManager;

public interface IDriveManagerService extends IService<DriveManager> {


    BaseResponse createOrUpdateDriveManager(DriveManager request);

    BaseResponse getDriveManagerById(String id);

    BaseResponse deleteById(String id);

    BaseResponse getDriveManagerList(DriveManager request);

    BaseResponse getDriveDownload();
}
