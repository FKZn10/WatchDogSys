package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocation;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/8 10:19
 */
public interface IFundAllocationService extends IService<FundAllocation> {

    BaseResponse getFundAllocationByDtlCon(String conId);
}
