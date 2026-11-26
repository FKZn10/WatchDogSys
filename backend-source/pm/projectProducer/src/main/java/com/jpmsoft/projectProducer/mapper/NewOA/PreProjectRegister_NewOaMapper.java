package com.jpmsoft.projectProducer.mapper.NewOA;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.NewOA.ProjectRegister_NewOaResponse;
import com.jpmsoft.common.model.response.Project.ProjectRegisterResponse;
import com.jpmsoft.projectProducer.pojo.entity.NewOA.PreProjectRegister_NewOa;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectRegister;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface PreProjectRegister_NewOaMapper extends BaseMapper<PreProjectRegister_NewOa> {

    List<ProjectRegisterResponse> getPreProjectRegister_NewOaList(@Param("params") Map<String, Object> params,
                                                            @Param("page") IPage<ProjectRegisterResponse> page);

    BigDecimal getEstimatedAmountSum(@Param("params") Map<String, Object> params);

    ProjectRegisterResponse getPreProjectRegister_NewOaById(@Param("prId") String prId);
}
