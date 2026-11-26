package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundAllocationDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAllocationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocationDtl;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAllocationDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:07
 */
@Service
public class FundAllocationDtlImpl extends ServiceImpl<FundAllocationDtlMapper, FundAllocationDtl>
implements IFundAllocationDtlService {


    @Override
    public BaseResponse getFundAllocationDtlList(String conId) {
        return BaseResponse.ok(baseMapper.getFundAllocationDtlList(conId));
    }

    @Override
    public List<FundAllocationDtlDTO> getFundAllocationDtlListByConId(String conId) {
        return baseMapper.getFundAllocationDtlList(conId);
    }

    @Override
    public List<FundAllocationDtl> getiFundActionDtlPayList(List<String> payIds) {
        return  baseMapper.getiFundActionDtlPayList(payIds);
    }
}
