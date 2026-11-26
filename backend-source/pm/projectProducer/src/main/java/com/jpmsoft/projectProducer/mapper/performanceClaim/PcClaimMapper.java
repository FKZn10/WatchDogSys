package com.jpmsoft.projectProducer.mapper.performanceClaim;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Claim.PcClaimResponse;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcClaim;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PcClaimMapper extends BaseMapper<PcClaim> {

    List<PcClaimResponse> getPcClaimList(@Param("page") IPage<PcClaimResponse> page, @Param("params") Map<String, Object> params);


    PcClaimResponse getPcClaimById(@Param("claimId") String claimId);
}
