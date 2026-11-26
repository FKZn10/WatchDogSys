package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectAcceptanceSettlementModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectAcceptanceSettlement;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectAcceptanceSettlementService extends IService<ProjectAcceptanceSettlement> {
    BaseResponse saveOrUpdateProjectAcceptanceSettlement(ProjectAcceptanceSettlementModel request);

    BaseResponse getProjectAcceptanceSettlementList(ProjectAcceptanceSettlementModel request, String token);

    BaseResponse getProjectAcceptanceSettlementDetail(String id);

    BaseResponse approvalData(ProjectAcceptanceSettlementModel request);

    BaseResponse calculateProjectAcceptanceSettlement(ProjectAcceptanceSettlementModel request);

    BaseResponse calculateProjectAcceptanceSettlementScore(ProjectAcceptanceSettlementModel request);

    BaseResponse importProjectAcceptanceSettlement(MultipartFile file, LogUserInfoVo userInfoByToken);
}
