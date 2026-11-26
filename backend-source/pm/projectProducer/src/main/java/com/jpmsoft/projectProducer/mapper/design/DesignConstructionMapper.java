package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Design.DesignConstructionResponse;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConstruction;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface DesignConstructionMapper extends BaseMapper<DesignConstruction> {

    List<DesignConstructionResponse> getDesignConstructionList(@Param("page") IPage<DesignConstructionResponse> page, @Param("params") Map<String, Object> params);

    BigDecimal getDcBudgetSum(@Param("params") Map<String, Object> params);

    DesignConstructionResponse getDesignConstructionById(@Param("dcId") String dcId);
}
