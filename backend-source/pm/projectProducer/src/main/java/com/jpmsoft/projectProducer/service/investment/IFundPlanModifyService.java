package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundPlanModify;

public interface IFundPlanModifyService extends IService<FundPlanModify> {
    BaseResponse createFundPlanModify(String ifrId);

    BaseResponse getFundPlanModifyList(FundPlanModifyDto request, String uId);

    BaseResponse getFundPlanModifyBypPid(String pId);

    BaseResponse updateFundPlanModify(FundPlanModifyDto request);

    BaseResponse getFundPlanModifyByPidAndYear(String pId, Integer year, String newDate);

    BaseResponse getFundPlanModifyHandleBypPid(String pId);
}
