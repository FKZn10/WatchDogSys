package com.jpmsoft.projectProducer.service.performanceClaim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Claim.PcClaimDtlRequest;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcClaimDtl;

import java.util.List;

public interface IPcClaimDtlService extends IService<PcClaimDtl> {

    void createOrUpdatePcClaimDtl(List<PcClaimDtlRequest> datas, String claimId);
}
