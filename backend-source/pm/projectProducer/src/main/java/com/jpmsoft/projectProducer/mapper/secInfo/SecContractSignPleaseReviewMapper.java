package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.SecInfo.SecContractSignPleaseReviewRequest;
import com.jpmsoft.common.model.response.SecInfo.SecContractSignPleaseReviewResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecContractSignPleaseReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecContractSignPleaseReviewMapper extends BaseMapper<SecContractSignPleaseReview> {


    List<SecContractSignPleaseReviewResponse> getContractSignPleaseReviewList(@Param("params") Map<String, Object> params, @Param("page") IPage<SecContractSignPleaseReviewRequest> page);


    SecContractSignPleaseReviewResponse getContractSignPleaseReviewById(@Param("csprId") String csprId);


}
