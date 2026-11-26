package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentActualAllocationModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentActualAllocation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface AgentActualAllocationMapper extends BaseMapper<AgentActualAllocation> {


    List<AgentActualAllocationModel> getAgentActualAllocationList(@Param("page") IPage<AgentActualAllocationModel> page, @Param("params") Map<String, Object> params);


    AgentActualAllocationModel getAgentActualAllocationById(@Param("aalId") String aalId);

    @Select("select sum(Aal_This_Pay) from  Agent_Actual_Allocation where Con_Id=#{conId} and Aal_State=7")
    BigDecimal getAalThisPayByConId(@Param("conId") String conId);

    @Select("select sum(Aal_This_Pay_Money_Exa) from  Agent_Actual_Allocation where Aal_Agent_Con_Id=#{aalAgentConId} and Aal_State=7")
    BigDecimal getAalThisPayMoneyByAgentConId(@Param("aalAgentConId") String aalAgentConId);
}
