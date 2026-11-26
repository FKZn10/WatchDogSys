package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecBiddingPlanChangeRes;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlanChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBiddingPlanChangeMapper extends BaseMapper<SecBiddingPlanChange> {

    List<SecBiddingPlanChangeRes> getSecBiddingPlanChangeList(@Param("params") Map<String, Object> params);

    Integer getSecBiddingPlanChangeCount(@Param("params") Map<String, Object> params);

    SecBiddingPlanChangeRes getSecBiddingPlanChangeById(@Param("bpcId") String bpcId);

    List<SecInfoChangeRes> getSecInfoChangeListByBpId(@Param("bpId") String bpId);
}
