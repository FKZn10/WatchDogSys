package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProvisionalEstimateModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProvisionalEstimate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecProvisionalEstimateMapper extends BaseMapper<SecProvisionalEstimate> {

    List<SecProvisionalEstimateModel> getSecProvisionalEstimateModelList(@Param("params") Map<String, Object> params);

    Integer getSecProvisionalEstimateModelListCount(@Param("params") Map<String, Object> params);

    SecProvisionalEstimateModel getSecProvisionalEstimateByPeId(@Param("peId") String peId);
}
