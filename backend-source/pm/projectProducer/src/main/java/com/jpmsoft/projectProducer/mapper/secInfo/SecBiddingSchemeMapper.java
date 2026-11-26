package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingScheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecBiddingSchemeMapper extends BaseMapper<SecBiddingScheme> {


    List<SecBiddingSchemeModel> getSecBiddingSchemeList(@Param("params") Map<String, Object> params);

    Integer getSecBiddingSchemeListCount(@Param("params") Map<String, Object> params);

    SecBiddingSchemeModel getSecBiddingSchemeById(@Param("bsId") String bsId);
}
