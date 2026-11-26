package com.jpmsoft.projectProducer.mapper.design;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignFeasibilityModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignFeasibility;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DesignFeasibilityMapper extends BaseMapper<DesignFeasibility> {


    List<DesignFeasibilityModel> getDesignFeasibilityList(@Param("page") IPage<DesignFeasibilityModel> page, @Param("params") Map<String, Object> params);


    DesignFeasibilityModel getDesignFeasibilityById(@Param("dfId") String dfId);
}
