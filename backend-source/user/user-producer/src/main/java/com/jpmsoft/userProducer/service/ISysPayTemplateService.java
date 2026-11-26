package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysPayTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.SysPayTemplate;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:08
 */
public interface ISysPayTemplateService extends IService<SysPayTemplate> {

    BaseResponse createOrUpdateSysPayTemplate(SysPayTemplateDto request);

    BaseResponse getSysPayTemplateList(SysPayTemplateDto request);

    BaseResponse getSysPayTemplateById(String id);

    BaseResponse deleteSysPayTemplateById(String id);
}
