package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.CalculationTender;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.TrackAuditMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvFinalAccountsApply;
import com.jpmsoft.projectProducer.pojo.entity.investment.TrackAuditMonthDtl;
import io.micrometer.core.instrument.Measurement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author: shanzhi
 * @date: 2024/09/02 10:45
 */
public interface MeasurementMapper extends BaseMapper<Measurement> {

    List<CalculationTender> getCalculationTenderPriceBySecId(@Param("pId") String pId,@Param("secId") String secId);
}
