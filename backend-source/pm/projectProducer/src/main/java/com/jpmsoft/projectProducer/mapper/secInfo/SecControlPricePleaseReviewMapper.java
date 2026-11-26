package com.jpmsoft.projectProducer.mapper.secInfo;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecControlPricePleaseReviewResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlPricePleaseReview;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecControlPricePleaseReviewMapper extends BaseMapper<SecControlPricePleaseReview> {


    List<SecControlPricePleaseReviewResponse> getControlPriceReviewList(@Param("params") Map<String, Object> params);


    SecControlPricePleaseReviewResponse getControlPriceReviewById(@Param("cpprId") String cpprId);

}

