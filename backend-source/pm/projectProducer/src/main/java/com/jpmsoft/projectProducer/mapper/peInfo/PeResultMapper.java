package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.PeResultResponse;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PeResultMapper extends BaseMapper<PeResult> {

    List<PeResultResponse> getPeResultList(@Param("params") Map<String, Object> params);

    Integer getPeResultListCount(@Param("params") Map<String, Object> params);

    PeResultResponse getPeResultByRid(@Param("rId") String rId);
}
