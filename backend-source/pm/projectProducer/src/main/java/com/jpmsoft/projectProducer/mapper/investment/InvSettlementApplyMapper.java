package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementApplyModel;
import com.jpmsoft.common.model.response.InvestmentControl.InvSettlementApplyResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvSettlementApplyMapper extends BaseMapper<InvSettlementApply> {


    List<InvSettlementApplyResponse> getInvSettlementApplyList(@Param("page") IPage<InvSettlementApplyResponse> page,
                                                               @Param("params") Map<String, Object> params);


    InvSettlementApplyResponse getInvSettlementApplyBysaId(@Param("saId") String saId);


    InvSettlementApplyResponse getLastApprovalInvSettlementApplyByConId(@Param("conId") String conId);

    List<InvSettlementApplyResponse> getLastApprovalInvSettlementApplyByProjectId(@Param("projectId") String projectId);

    List<InvSettlementApplyModel> getList(@Param("params")Map<String, Object> query, @Param("page")IPage<InvSettlementApplyModel> page);

    InvSettlementApplyModel getDetail(@Param("saId")String saId);
}