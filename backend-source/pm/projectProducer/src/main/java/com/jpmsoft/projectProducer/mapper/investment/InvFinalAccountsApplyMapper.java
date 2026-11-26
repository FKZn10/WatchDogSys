package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvFinalAccountsApplyResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFinalAccountsApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvFinalAccountsApplyMapper extends BaseMapper<InvFinalAccountsApply> {

    List<InvFinalAccountsApplyResponse> getInvFinalAccountsApplyList(@Param("page") IPage<InvFinalAccountsApplyResponse> page, @Param("params") Map<String, Object> params);


    InvFinalAccountsApplyResponse getInvFinalAccountsApplyByfaId(@Param("faId") String faId);

    InvFinalAccountsApplyResponse getInvFinalAccountsApplyAndDtlByPid(@Param("pId") String pId);

    InvFinalAccountsApplyResponse getInvFinalAccountsApplyConId(@Param("conId") String conId);



}
