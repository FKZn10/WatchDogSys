package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolutionInfo;

public interface IPreProjectSolutionInfoService extends IService<PreProjectSolutionInfo> {
    BaseResponse createOrUpdatePreProjectSolutionInfo(PreProjectSolutionInfoModel request);

    BaseResponse approvalPreProjectSolutionInfo(PreProjectSolutionInfoModel request);

    BaseResponse getPreProjectSolutionInfoList(PreProjectSolutionInfoModel request, String token);

    BaseResponse getPreProjectSolutionInfoById(String id);

    BaseResponse createOrUpdatePreProjectSolutionInfoWithOutFlow(PreProjectSolutionInfoModel request);
}
