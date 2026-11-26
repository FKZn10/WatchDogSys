package com.jpmsoft.projectProducer.mapper.performanceClaim;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Claim.PcAwardTicketResponse;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcAwardTicket;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PcAwardTicketMapper extends BaseMapper<PcAwardTicket> {

    List<PcAwardTicketResponse> getPcAwardTicketList(@Param("page") IPage<PcAwardTicketResponse> page, @Param("params") Map<String, Object> params);


    PcAwardTicketResponse getPcAwardTicketById(@Param("atId") String atId);

    List<PcAwardTicketResponse> getPcAwardTicketByConPay(@Param("payId") String payId);

    Integer refreshConPayId2Null(@Param("atIds") List<String> atIds);

    Integer refreshConPayId(@Param("atIds") List<String> atIds,@Param("payId") String payId);
}
