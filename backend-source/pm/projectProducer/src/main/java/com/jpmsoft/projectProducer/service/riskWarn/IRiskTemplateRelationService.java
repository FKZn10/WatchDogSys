package com.jpmsoft.projectProducer.service.riskWarn;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskTemplateRelationDTO;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskTemplateRelation;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 13:45
 */
public interface IRiskTemplateRelationService extends IService<RiskTemplateRelation> {

    boolean havingPreventionMeasure(String rpmId);

    BaseResponse createOrUpdate(RiskTemplateRelationDTO request);

    BaseResponse createOrUpdateBath(List<RiskTemplateRelationDTO> request,String teId);

    BaseResponse queryList(RiskTemplateRelationDTO request,String uId);

    BaseResponse deleteById(String id);
}
