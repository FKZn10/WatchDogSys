package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowSumDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConPayApplyFlowVo;

import java.util.List;

public interface IConPayApplyFlowService extends IService<ConPayApplyFlow> {
    BaseResponse getConPayApplyFlowList(ConPayApplyFlowDto request);

    BaseResponse getConPayApplyFlowById(String id);

    BaseResponse getConPayApplyFlowByConId(String conId);

    BaseResponse createOrUpdateConPayApplyFlow(ConPayApplyFlowDto request);

    BaseResponse getConPayApplyFlowSumById(ConPayApplyFlowSumDto request);

    BaseResponse getConPayApplyFlowSumByConId(String conId);

    BaseResponse getConPayApplyFlowDataList(ConPayApplyFlowVo vo);

    BaseResponse getConPayApplyFlowStatsList(ConPayApplyFlowVo vo);

    List<ConPayApplyFlow> getConPayById(String payId);

}
