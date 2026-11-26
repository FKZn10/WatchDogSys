package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvanceList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;


public interface AgentFundAdvanceListMapper extends BaseMapper<AgentFundAdvanceList> {

    @Select("select sum(afal.Afal_This_Money) from  Agent_Fund_Advance_List afal " +
            " left join  Agent_Fund_Advance afa on afa.Afa_Id=afal.Afa_Id " +
            " where afal.Afal_Con_Id=#{conId} and afa.Afa_State=7 ")
    BigDecimal getAfalThisMoneyByAfalConId(@Param("conId") String afalConId);
}
