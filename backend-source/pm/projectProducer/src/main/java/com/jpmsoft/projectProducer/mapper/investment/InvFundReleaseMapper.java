package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.InvestmentControl.InvFundReleaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFundRelease;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvFundReleaseMapper extends BaseMapper<InvFundRelease> {

    InvFundReleaseResponse getInvFundReleaseByIfrId(@Param("ifrId") String ifrId);

    List<InvFundReleaseResponse> getInvFundReleaseList(@Param("params") Map<String, Object> params);

    Integer getInvFundReleaseListCount(@Param("params") Map<String, Object> params);
}
