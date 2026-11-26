package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPortalDesignTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalDesignTemplate;

import java.util.List;

public interface ISysPortalDesignTemplateService extends IService<SysPortalDesignTemplate> {
    BaseResponse GetSysPortalDesignTemplateList(SysPortalDesignTemplateDto ruquest);


    BaseResponse CreateOrUpdateSysPortalDesignTemplate(SysPortalDesignTemplateDto ruquest);



    BaseResponse importSysPortalDesignTemplate(List<SysPortalDesignTemplateDto> ruquest);
}
