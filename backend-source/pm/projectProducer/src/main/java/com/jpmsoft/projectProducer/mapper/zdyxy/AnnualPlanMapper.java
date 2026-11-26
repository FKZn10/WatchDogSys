package com.jpmsoft.projectProducer.mapper.zdyxy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.AnnualPlanModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.AnnualPlan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AnnualPlanMapper extends BaseMapper<AnnualPlan> {
    List<AnnualPlanModel> getList(@Param("params")Map<String, Object> query,
                                  @Param("page")IPage<AnnualPlanModel> page);

    AnnualPlanModel getDetail(@Param("id")String id);

    AnnualPlanModel getInfo(@Param("yearStart") Date yearStart, @Param("yearEnd") Date yearEnd);
}