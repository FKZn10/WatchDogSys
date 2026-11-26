package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.SecInfo.SecBiddingNegotiationDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingNegotiation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecBiddingNegotiationMapper extends BaseMapper<SecBiddingNegotiation> {
    SecBiddingNegotiationDto getSecBiddingNegotiationByBnId(@Param("bnId") String bnId);


    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") Map<String, Object> query);

    List<SecBiddingNegotiationDto> getSecBiddingNegotiationListClass(@Param("authSql") String authSql, @Param("page") IPage<SecBiddingNegotiationDto> page,@Param("params") Map<String, Object> query);

}
