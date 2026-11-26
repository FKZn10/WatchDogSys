package com.jpmsoft.projectProducer.service.yw;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.yw.ProjectAcceptanceCalculationAuditModel;
import com.jpmsoft.projectProducer.pojo.entity.yw.ProjectAcceptanceCalculationAudit;

public interface ProjectAcceptanceCalculationAuditService extends IService<ProjectAcceptanceCalculationAudit> {
    BaseResponse saveOrUpdateData(ProjectAcceptanceCalculationAuditModel dto);

    BaseResponse approvalData(ProjectAcceptanceCalculationAuditModel request);

    BaseResponse getList(ProjectAcceptanceCalculationAuditModel request, String uid);

    BaseResponse getDetail(String id);

    void generatePACA(ProjectAcceptanceCalculationAudit entity);

    BaseResponse approvalData2(ProjectAcceptanceCalculationAuditModel request);

    BaseResponse refreshAll(ProjectAcceptanceCalculationAuditModel request);
}