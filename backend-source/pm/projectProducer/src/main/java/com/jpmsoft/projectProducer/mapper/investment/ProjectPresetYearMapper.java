package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.InvestmentControl.ProjectPresetYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.ProjectPresetYearResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.ProjectPresetYear;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectPresetYearMapper  extends BaseMapper<ProjectPresetYear> {
    IPage<ProjectPresetYearResponse> getProjectPresetYearList(IPage<ProjectPresetYear> page, @Param("request") ProjectPresetYearRequest request);

    ProjectPresetYearResponse getProjectPresetYearId(@Param("id") String id);
}
