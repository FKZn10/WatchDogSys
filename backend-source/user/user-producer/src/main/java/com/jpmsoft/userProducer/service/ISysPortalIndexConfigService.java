package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexConfigDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexConfig;

public interface ISysPortalIndexConfigService extends IService<SysPortalIndexConfig> {

    BaseResponse createOrUpdateSysPortalIndexConfig(SysPortalIndexConfigDto request, String token);

    BaseResponse updatePortalStateById(SysPortalIndexConfigDto request, String token);

    BaseResponse getSysPortalIndexConfigById(String id);

    BaseResponse getSysPortalIndexConfigList(SysPortalIndexConfigDto request);

    BaseResponse deleteSysPortalIndexConfigById(String id);

    BaseResponse queryComponentListByUserInfo(String token);


}
