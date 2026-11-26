package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.InvestmentControl.InvAdjustmentTemplateResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplate;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvAdjustmentTemplate;
import com.jpmsoft.projectProducer.pojo.vo.investment.BudgetCompanyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface InvAdjustmentTemplateMapper extends BaseMapper<InvAdjustmentTemplate> {

    List<InvAdjustmentTemplateResponse> getAdjustmentTemplateList(@Param("page") IPage<InvAdjustmentTemplateResponse> page, @Param("params") Map<String, Object> params);


    InvAdjustmentTemplateResponse getAdjustmentTemplateByAtId(@Param("atId") String atId);

}
