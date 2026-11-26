package com.jpmsoft.projectProducer.mapper.completion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Completion.CompletionAcceptanceAllResponse;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceAll;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CompletionAcceptanceAllMapper extends BaseMapper<CompletionAcceptanceAll> {

    List<CompletionAcceptanceAllResponse> getCompletionAcceptanceAllList(@Param("page") IPage<CompletionAcceptanceAllResponse> page, @Param("params") Map<String, Object> params);


    CompletionAcceptanceAllResponse getCompletionAcceptanceAllById(@Param("caaId") String caaId);
}
