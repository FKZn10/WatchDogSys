package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Project.ProjectRegisterResponse;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface PreProjectRegisterMapper extends BaseMapper<PreProjectRegister> {

    List<ProjectRegisterResponse> getPreProjectRegisterList(@Param("params") Map<String, Object> params,
                                                            @Param("page")  IPage<ProjectRegisterResponse> page);

    BigDecimal getEstimatedAmountSum(@Param("params") Map<String, Object> params);

    ProjectRegisterResponse getPreProjectRegisterById(@Param("prId") String prId);

    List<ProjectRegisterResponse> getPreProjectRegisterListWithoutFlowStop(@Param("params")Map<String, Object> query,@Param("page") IPage<ProjectRegisterResponse> page);
}
