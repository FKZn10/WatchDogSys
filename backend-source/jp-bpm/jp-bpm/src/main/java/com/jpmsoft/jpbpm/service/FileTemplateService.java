package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.FilerecordRequest;
import com.jpmsoft.jpbpm.pojo.dto.TemplateinitializationDto;
import com.jpmsoft.jpbpm.pojo.entity.FileTemplate;
import org.springframework.stereotype.Service;

@Service
public interface FileTemplateService extends IService<FileTemplate> {

    /**
     * 获取流程文档模板信息
     *
     * @param request
     * @return
     */
    BaseResponse getTemplate(TemplateinitializationDto request,String customerId);

    BaseResponse getTemplatebyfileid(FilerecordRequest request,String customerId);

    FileTemplate getTemplatebyTCode(String tCode,String customerId);

    BaseResponse  getTemplateList(FilerecordRequest requst,String customerId);
}
