package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApply;

public interface IConPayApplyService extends IService<ConPayApply> {
    BaseResponse getConPayApplyList(ConPayApplyDto request);

    BaseResponse getConPayApplyById(String id);

    BaseResponse createConPayApply(String teId);
}
