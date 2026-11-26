package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.ConPayPathDtlRequest;
import com.jpmsoft.common.model.response.pay.ConPayPathDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayPathDtl;

import java.util.List;

public interface IConPayPathDtlService extends IService<ConPayPathDtl> {
    BaseResponse createOrUpdateConPayPathDtl(List<ConPayPathDtlRequest> conPayPathDtls, String payId);

    List<ConPayPathDtlResponse> getConPayPathDtlByPayId(String payId);
}
