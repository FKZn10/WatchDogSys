package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvRoughDeclareResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRoughDeclare;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvRoughDeclareMapper extends BaseMapper<InvRoughDeclare> {

    List<InvRoughDeclareResponse> getInvRoughDeclareList(@Param("page") IPage<InvRoughDeclareResponse> page, @Param("params") Map<String, Object> params);


    InvRoughDeclareResponse getInvRoughDeclareByRdId(@Param("bdId") String rdId);

    InvRoughDeclareResponse getInvRoughDeclareAndDtlByPid(@Param("pId") String pId);
}
