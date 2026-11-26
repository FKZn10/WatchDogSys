package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBidSecurityModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBidSecurity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBidSecurityMapper extends BaseMapper<SecBidSecurity> {

    List<SecBidSecurityModel> getSecBidSecurityList(@Param("params") Map<String, Object> params);

    Integer getSecBidSecurityListCount(@Param("params") Map<String, Object> params);

    SecBidSecurityModel getSecBidSecurityById(@Param("sbsId") String sbsId);
}
