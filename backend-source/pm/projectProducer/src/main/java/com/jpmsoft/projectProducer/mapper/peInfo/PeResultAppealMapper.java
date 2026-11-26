package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.PeResultAppealResponse;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeResultAppeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PeResultAppealMapper extends BaseMapper<PeResultAppeal> {

    List<PeResultAppealResponse> getResultAppealList(@Param("params") Map<String, Object> params);

    Integer getResultAppealListCount(@Param("params") Map<String, Object> params);
}