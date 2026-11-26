package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.ApprovalOfGreeningSchemeRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ApprovalOfGreeningScheme;

public interface IApprovalOfGreeningSchemeService extends IService<ApprovalOfGreeningScheme> {
    /**
     * .绿化方案审批
     */
    BaseResponse createApprovalOfGreeningScheme(ApprovalOfGreeningSchemeRequest request);

    BaseResponse getApprovalOfGreeningSchemeList(ApprovalOfGreeningSchemeRequest request);

    BaseResponse getApprovalOfGreeningSchemeInfoById(String uuId);

    BaseResponse deleteApprovalOfGreeningScheme(String uuId);

    BaseResponse updateApprovalOfGreeningScheme(ApprovalOfGreeningSchemeRequest request);

    BaseResponse updateApproveStatusApprovalOfGreeningScheme(ApprovalOfGreeningSchemeRequest request);

}
