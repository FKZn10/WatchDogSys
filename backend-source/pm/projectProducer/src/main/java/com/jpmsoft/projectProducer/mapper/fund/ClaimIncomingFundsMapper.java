package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.fund.ClaimIncomingFundsRequest;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import com.jpmsoft.common.model.response.fund.ClaimIncomingFundsResponse;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFunds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 来款认领 Mapper
 *
 * @author tianhelin
 * @date 2025/11/17
 */
@Mapper
public interface ClaimIncomingFundsMapper extends BaseMapper<ClaimIncomingFunds> {
    List<ClaimIncomingFundsResponse> getClaimIncomingFundsPage(@Param("page") Page<ClaimIncomingFundsResponse> page, @Param("params") Map<String, Object> query);

    List<ClaimIncomingFundsDtlDTO> getClaimIncomingFundsByPid(@Param("page") Page<ClaimIncomingFundsDtlDTO> page, @Param("params") ClaimIncomingFundsRequest request);
}