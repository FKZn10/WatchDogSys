package com.jpmsoft.projectProducer.service.leaderBoard;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.loadBoard.*;

public interface LeaderBoardService {
    BaseResponse getPreProjectInfo(ProjectLoadRequest request);

    BaseResponse getPreProjectInfoList(ProjectLoadRequest request);

    BaseResponse getContractInfo(ContractLoadRequest request);

    BaseResponse getContractInfoList(ContractLoadRequest request);

    BaseResponse getSiteManagementInfo(SiteManagementRequest request);

    BaseResponse getSiteManagementInfoCompare(SiteManagementRequest request);

    BaseResponse getSiteManagementInfoList(SiteManagementRequest request);

    BaseResponse getPayInfo(PayLoadRequest request);

    BaseResponse getPayInfoList(PayLoadRequest request);

    BaseResponse getPlanInfo(PlanLoadRequest request);

    BaseResponse getPlanInfoList(PlanLoadRequest request);

    BaseResponse getAnnualMaintenanceBudgetInfo(ProjectLoadRequest request);

    BaseResponse getAnnualMaintenanceBudgetInfoList(ProjectLoadRequest request);
}