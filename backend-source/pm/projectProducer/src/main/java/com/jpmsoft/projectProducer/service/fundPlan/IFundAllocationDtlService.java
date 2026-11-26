package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAllocationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocationDtl;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:07
 */
public interface IFundAllocationDtlService extends IService<FundAllocationDtl> {


    BaseResponse getFundAllocationDtlList(String conId);


    List<FundAllocationDtlDTO> getFundAllocationDtlListByConId(String conId);

    List<FundAllocationDtl> getiFundActionDtlPayList(List<String> payIds);
}
