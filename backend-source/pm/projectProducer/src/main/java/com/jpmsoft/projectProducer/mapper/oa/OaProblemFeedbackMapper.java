package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProblemFeedbackModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProblemFeedback;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问题反馈
 */

public interface OaProblemFeedbackMapper extends BaseMapper<OaProblemFeedback> {

    List<OaProblemFeedbackModel> getOaProblemFeedbackList(@Param("page") IPage<OaProblemFeedbackModel> page, @Param("params") Map<String, Object> params);

    OaProblemFeedbackModel getOaProblemFeedbackById(@Param("pmfId") String pmfId);

    List<OaProblemFeedbackModel> getOaProblemFeedbackByPmdId(@Param("pmdId") String pmdId);
}
