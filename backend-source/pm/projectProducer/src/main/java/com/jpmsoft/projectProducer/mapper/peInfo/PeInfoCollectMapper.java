package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.PeInfoCollectResponse;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PeInfoCollectMapper extends BaseMapper<PeInfoCollect> {

    List<PeInfoCollectResponse> getPeInfoCollectList(@Param("params") Map<String, Object> params);

    Integer getPeInfoCollectListCount(@Param("params") Map<String, Object> params);

    PeInfoCollectResponse getPeInfoCollectByPecId(@Param("pecId") String pecId);
}
