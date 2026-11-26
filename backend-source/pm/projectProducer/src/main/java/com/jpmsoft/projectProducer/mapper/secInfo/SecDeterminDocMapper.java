package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecDeterminDocResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDeterminDoc;
import org.apache.ibatis.annotations.Param;


public interface SecDeterminDocMapper extends BaseMapper<SecDeterminDoc> {

    SecDeterminDocResponse getSecDeterminDocByBdId(@Param("bdId") String bdId);
}
