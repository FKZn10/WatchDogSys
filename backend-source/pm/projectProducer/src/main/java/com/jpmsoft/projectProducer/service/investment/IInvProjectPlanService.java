package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvProjectPlanRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvProjectPlan;

import java.util.List;

public interface IInvProjectPlanService extends IService<InvProjectPlan> {

    void createOrUpdateInvProjectPlan(List<InvProjectPlanRequest> datas, String ifrId);

    BaseResponse getInvProjectPlanList(InvProjectPlanRequest request, String uId);

    BaseResponse getInvProPlanHistoryAmt(String pId, String year);

    BaseResponse createOrUpdateInvProjectPlan(InvProjectPlanRequest request);

    BaseResponse getInvProjectPlanById(String ippId);

    BaseResponse getInvProjectPlanByPIdAndYear(String pId, Integer year, String fundsType);

    List<InvProjectPlan> getInvProjectPlanListByPids(List<String> pIds);
}
