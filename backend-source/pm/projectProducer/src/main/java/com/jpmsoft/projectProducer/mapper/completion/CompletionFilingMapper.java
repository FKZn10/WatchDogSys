package com.jpmsoft.projectProducer.mapper.completion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Completion.CompletionFilingResponse;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionFiling;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CompletionFilingMapper extends BaseMapper<CompletionFiling> {


    List<CompletionFilingResponse> getCompletionFilingList(@Param("page") IPage<CompletionFilingResponse> page, @Param("params") Map<String, Object> params);


    CompletionFilingResponse getCompletionFilingById(@Param("cfId") String cfId);
}
