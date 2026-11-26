package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvEstimateDeclareResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvEstimateDeclare;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvEstimateDeclareMapper extends BaseMapper<InvEstimateDeclare> {

    List<InvEstimateDeclareResponse> getInvEstimateDeclareList(@Param("page") IPage<InvEstimateDeclareResponse> page, @Param("params") Map<String, Object> params);


    InvEstimateDeclareResponse getInvEstimateDeclareByEdId(@Param("bdId") String edId);

    InvEstimateDeclareResponse getInvEstimateDeclareAndDtlByPid(@Param("pId") String pId);
}
