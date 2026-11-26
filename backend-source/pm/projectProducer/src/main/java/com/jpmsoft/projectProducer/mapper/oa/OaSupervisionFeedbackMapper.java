package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Oa.OaSupervisionFeedbackResponse;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaSupervisionFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface OaSupervisionFeedbackMapper extends BaseMapper<OaSupervisionFeedback> {


    List<OaSupervisionFeedbackResponse> getOaSupervisionFeedbackList(@Param("page") IPage<OaSupervisionFeedbackResponse> page, @Param("params") Map<String, Object> params);


    OaSupervisionFeedbackResponse getOaSupervisionFeedbackBySfId(@Param("sfId") String sfId);
}
