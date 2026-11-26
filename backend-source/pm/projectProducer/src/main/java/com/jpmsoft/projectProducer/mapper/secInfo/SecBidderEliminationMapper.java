package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidderEliminationModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidderElimination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBidderEliminationMapper extends BaseMapper<SecBidderElimination> {

    List<SecBidderEliminationModel> getSecBidderEliminationList(@Param("params") Map<String, Object> params);

    Integer getSecBidderEliminationListCount(@Param("params") Map<String, Object> params);

    SecBidderEliminationModel getSecBidderEliminationById(@Param("sbeId") String sbeId);
}
