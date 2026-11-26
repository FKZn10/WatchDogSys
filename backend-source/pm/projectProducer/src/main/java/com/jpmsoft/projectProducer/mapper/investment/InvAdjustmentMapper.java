package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvAdjustmentResponse;
import com.jpmsoft.common.model.response.InvestmentControl.InvAdjustmentTemplateResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustment;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvAdjustmentMapper extends BaseMapper<InvAdjustment> {

    List<InvAdjustmentResponse> getAdjustmentList(@Param("page") IPage<InvAdjustmentResponse> page, @Param("params") Map<String, Object> params);


    InvAdjustmentResponse getAdjustmentByIaId(@Param("iaId") String iaId);

}
