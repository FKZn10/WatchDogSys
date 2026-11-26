package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementEndVO;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementEnd;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvSettlementEndMapper extends BaseMapper<InvSettlementEnd> {


    List<InvSettlementEndVO> getInvSettlementEndList(@Param("page") IPage<InvSettlementEndVO> page, @Param("params") Map<String, Object> params);


    List<InvSettlementEndVO> getInvSettlementEndNoAuthList(@Param("page") IPage<InvSettlementEndVO> page, @Param("params") Map<String, Object> params);


    InvSettlementEndVO getInvSettlementEndBySaeId(@Param("saeId") String saeId);


    InvSettlementEndVO getLastApprovalInvSettlementEndByConId(@Param("conId") String conId);

    InvSettlementEndVO getLastApprovalInvSettlementEndByPayId(@Param("payId")String payId);

    Integer refreshApplySum(@Param("ids") List<String> ids);
}
