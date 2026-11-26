package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBrightMoonModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBrightMoon;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBrightMoonMapper extends BaseMapper<SecBrightMoon> {


    List<SecBrightMoonModel> getSecBrightMoonList(@Param("params") Map<String, Object> params);

    Integer getSecBrightMoonListCount(@Param("params") Map<String, Object> params);

    SecBrightMoonModel getSecBrightMoonById(@Param("sbmId") String sbmId);
}
