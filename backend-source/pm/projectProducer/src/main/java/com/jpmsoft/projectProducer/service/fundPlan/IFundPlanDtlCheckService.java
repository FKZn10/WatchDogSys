package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtlCheck;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:05
 */
public interface IFundPlanDtlCheckService extends IService<FundPlanDtlCheck> {

    void delFundPlanDtlCheckByFpdIds(List<String> fpdIds);

    BaseResponse createOrUpdateFundPlanDtlCheck(FundPlanDtlCheckDto request);

    BaseResponse getFundPlanDtlCheckList(FundPlanDtlCheckDto request);

    BaseResponse getFundPlanDtlCheckById(String id);
}
