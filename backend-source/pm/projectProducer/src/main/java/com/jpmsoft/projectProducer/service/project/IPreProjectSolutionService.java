package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolution;

public interface IPreProjectSolutionService extends IService<PreProjectSolution> {
    BaseResponse createOrUpdatePreProjectSolution(PreProjectSolutionModel request);

    BaseResponse deletePreProjectSolution(PreProjectSolutionModel request);

    BaseResponse getPreProjectSolutionList(PreProjectSolutionModel request, String uId);

    BaseResponse getPreProjectSolutionById(String id);

    BaseResponse approvalPreProjectSolution(PreProjectSolutionModel request);

    void refreshInfo();

    BaseResponse modifyPreProjectSolution(PreProjectSolutionModel request);

    BaseResponse getPreProjectSolutionListModifyInfo(PreProjectSolutionModel request, String uid);
}
