package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface AgentFundAdvanceMapper extends BaseMapper<AgentFundAdvance> {

    List<AgentFundAdvanceModel> getAgentFundAdvanceList(@Param("page") IPage<AgentFundAdvanceModel> page, @Param("params") Map<String, Object> params);


    AgentFundAdvanceModel getAgentFundAdvanceById(@Param("afaId") String afaId);

    @Select("select sum(Afa_This_Money) from  Agent_Fund_Advance where Con_Id=#{conId} and Afa_State=7")
    BigDecimal getAfaThisMoneyByConId(@Param("conId") String conId);


    List<AgentFundAdvance> getFundAdvanceNotConPayApply(@Param("teId") String teId);
}
