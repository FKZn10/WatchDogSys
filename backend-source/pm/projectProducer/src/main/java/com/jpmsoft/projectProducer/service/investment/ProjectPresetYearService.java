package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.ProjectPresetYearRequest;
import com.jpmsoft.projectProducer.pojo.entity.investment.ProjectPresetYear;

public interface ProjectPresetYearService extends IService<ProjectPresetYear> {
    BaseResponse getProjectPresetYearId(String id);

    BaseResponse getProjectPresetYearList(ProjectPresetYearRequest request);

    BaseResponse saveOrUpdatePresetYear(ProjectPresetYearRequest request, String token);
}
