package com.jpmsoft.projectProducer.mapper.completion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.Construction.CompletionAcceptanceRequest;
import com.jpmsoft.common.model.response.Construction.CompletionAcceptanceResponse;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptance;
import com.jpmsoft.projectProducer.pojo.vo.completion.CompletionAcceptanceMoneySumVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CompletionAcceptanceMapper extends BaseMapper<CompletionAcceptance> {


    List<CompletionAcceptanceResponse> getCompletionAcceptanceList(@Param("page") IPage<CompletionAcceptanceResponse> page, @Param("params") Map<String, Object> params);

    CompletionAcceptanceMoneySumVo getCompletionAcceptanceMoneySum(@Param("params") Map<String, Object> params);

    CompletionAcceptanceResponse getCompletionAcceptanceById(@Param("caId") String caId);

    CompletionAcceptanceResponse  getCompletionAcceptanceLastByPidAndType(@Param("params")  CompletionAcceptanceRequest params);
}
