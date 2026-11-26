package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundAllocationMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAllocationDTO;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAllocationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocation;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAllocationDtlService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAllocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/8 10:19
 */
@Service
public class FundAllocationImpl extends ServiceImpl<FundAllocationMapper, FundAllocation> implements IFundAllocationService {

    @Resource
    private IFundAllocationDtlService fundAllocationDtlService;

    @Override
    public BaseResponse getFundAllocationByDtlCon(String conId) {
        List<FundAllocationDtlDTO> dtls=fundAllocationDtlService.getFundAllocationDtlListByConId(conId);
        if (!CollectionUtils.isEmpty(dtls))
        {
            List<FundAllocationDTO> data=baseMapper.getFundAllocationByDtlCon(dtls.stream().map(FundAllocationDtlDTO::getFaId).collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(data)) {
                data.forEach(d->{
                    d.setFundAllocationDtls(dtls.stream().filter(f-> ValidateUtil.isNotEmpty(f.getFaId())&&f.getFaId().equals(d.getId())).collect(Collectors.toList()));
                });
            }
            return BaseResponse.ok(data);
        }
        return BaseResponse.ok();
    }
}
