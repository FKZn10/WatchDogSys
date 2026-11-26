package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.dto.SysFileTemplateApiFieldDto;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplateApiField;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysFileTemplateApiFieldService extends IService<SysFileTemplateApiField> {

    BaseResponse createOrUpdateFileTemplateApiField(List<SysFileTemplateApiFieldDto> fields, String tpId);


    Boolean can2DelFileTemplatePrint(String tpId);
}
