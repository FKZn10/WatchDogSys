package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConTemplateDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConTemplate;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 10:06
 */
public interface IConTemplateService extends IService<ConTemplate> {
    BaseResponse createConTemplate(ConTemplateDto request);

    BaseResponse updateConTemplate(ConTemplateDto request);

    BaseResponse getConTemplateList(ConTemplateDto request, String uId);

    BaseResponse getConTemplateById(String id);

    BaseResponse updateConTemplateHistory(ConTemplateDto request);
}
