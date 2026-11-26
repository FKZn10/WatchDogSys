package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAppropriation;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.ConAppropriationStatisticsVo;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundAppropriationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:29
 */
public interface FundAppropriationMapper extends BaseMapper<FundAppropriation> {

    List<FundAppropriationVo> getFundAppropriationList(@Param("page") IPage<FundAppropriationVo> page, @Param("params") Map<String, Object> params);

    FundAppropriationVo getFundAppropriationById(@Param("id") String id);

    @Select("select sum(Fa_Investment_Actual) from  Fund_Appropriation where P_Id=#{pId} and State=7 ")
    BigDecimal getProCompletedInvSum(@Param("pId") String pId);

    ConAppropriationStatisticsVo getConAppropriationDtl(@Param("conId") String conId);

    @Select("select sum(Fa_Demand) from  Fund_Appropriation where P_Id=#{pId} and State=7 ")
    BigDecimal getFaDemandSumByPid(@Param("pId") String pId);
}
