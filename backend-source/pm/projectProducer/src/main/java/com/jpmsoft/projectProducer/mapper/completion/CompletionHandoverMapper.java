package com.jpmsoft.projectProducer.mapper.completion;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Completion.CompletionHandoverResponse;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionHandover;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface CompletionHandoverMapper extends BaseMapper<CompletionHandover> {


    List<CompletionHandoverResponse> getCompletionHandoverList(@Param("page") IPage<CompletionHandoverResponse> page, @Param("params") Map<String, Object> params);


    CompletionHandoverResponse getCompletionHandoverById(@Param("chId") String chId);
}
