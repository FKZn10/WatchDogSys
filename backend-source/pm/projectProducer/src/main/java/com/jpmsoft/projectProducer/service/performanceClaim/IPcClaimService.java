package com.jpmsoft.projectProducer.service.performanceClaim;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Claim.PcClaimRequest;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcClaim;

public interface IPcClaimService extends IService<PcClaim> {


    BaseResponse createOrUpdatePcClaim(PcClaimRequest request);

    BaseResponse getPcClaimList(PcClaimRequest request, String uId);

    BaseResponse getPcClaimById(String claimId);
}
