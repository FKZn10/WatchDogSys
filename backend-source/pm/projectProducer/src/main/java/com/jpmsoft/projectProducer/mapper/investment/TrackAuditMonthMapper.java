package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.TrackAuditMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementSecond;
import com.jpmsoft.projectProducer.pojo.entity.investment.TrackAuditMonthDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvSettlementSecondVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: shanzhi
 * @date: 2024/06/28 17:10
 */
public interface TrackAuditMonthMapper extends BaseMapper<TrackAuditMonthDtl> {

    @Select("select * from  Track_Audit_Month where Con_Id=#{conId} and State=7")
    List<TrackAuditMonthDtl> getTrackAuditMonthByConId(@Param("conId") String conId);

    List<TrackAuditMonth> getTrackAuditMonthListByPId(@Param("pId") String pId);

}
