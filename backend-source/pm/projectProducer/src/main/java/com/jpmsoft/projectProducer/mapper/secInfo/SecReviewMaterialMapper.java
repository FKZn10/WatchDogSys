package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecReviewMaterialResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecReviewMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecReviewMaterialMapper extends BaseMapper<SecReviewMaterial> {


    void deleteByBusinessId(@Param("sourceType") String sourceType,@Param("businessId") String businessId);

    List<SecReviewMaterialResponse> getReviewMaterialList(@Param("sourceType") String sourceType,@Param("businessId") String businessId);

}
