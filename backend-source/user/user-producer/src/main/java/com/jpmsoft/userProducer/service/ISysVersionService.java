package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysVersionDto;
import com.jpmsoft.userProducer.pojo.entity.SysVersion;

public interface ISysVersionService extends IService<SysVersion> {

    BaseResponse createOrUpdateSysVersion(SysVersionDto requset);

    BaseResponse getSysVersionModelList(SysVersionDto request);

    BaseResponse getSysVersionModelById(String vId);

    BaseResponse getDatabaseExpiredDate();
}
