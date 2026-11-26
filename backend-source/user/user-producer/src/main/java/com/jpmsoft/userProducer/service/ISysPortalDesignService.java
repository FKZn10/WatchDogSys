package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPortalDesignDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesign;

import java.util.List;

public interface ISysPortalDesignService extends IService<SysPortalDesign> {

    BaseResponse GetSysPortalDesignList(SysPortalDesignDto ruquest);


    BaseResponse CreateOrUpdateSysPortalDesign(SysPortalDesignDto ruquest);



    BaseResponse importSysPortalDesign(List<SysPortalDesignDto> ruquest);
}
