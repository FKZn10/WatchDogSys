package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fund.GroupFundPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.GroupFundPlan;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.request.fund.GroupFundPlanRequest;

public interface IGroupFundPlanService extends IService<GroupFundPlan> {
    BaseResponse createOrUpdateGroupFundPlan(GroupFundPlanDto request);

    BaseResponse getGroupFundPlanList(GroupFundPlanRequest request,String uid);

    BaseResponse getGroupFundPlanById(String id);

    BaseResponse getFundInfo(GroupFundPlanRequest request);

    BaseResponse getContractFundInfo(GroupFundPlanRequest request);

    BaseResponse getFundPlanInfo(GroupFundPlanRequest request);

    BaseResponse getProjectInfo(String pId);

    BaseResponse getGroupFundPlanDetail(GroupFundPlanRequest request);

    BaseResponse deleteGroupFundPlanById(GroupFundPlanRequest request);

    BaseResponse refreshHistoryData(GroupFundPlanDto dto);

    BaseResponse returnInitiatorToTask(ReturnInitiatorDto request, String token);

    BaseResponse existProject(GroupFundPlanRequest request);
}
