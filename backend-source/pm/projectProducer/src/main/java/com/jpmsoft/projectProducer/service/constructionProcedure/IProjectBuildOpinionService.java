package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.ProjectBuildOpinionRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectBuildOpinion;

public interface IProjectBuildOpinionService extends IService<ProjectBuildOpinion> {
    /**
     * .建设项目选址意见书
     */
    BaseResponse createProjectBuildOpinion(ProjectBuildOpinionRequest request);

    BaseResponse getProjectBuildOpinionList(ProjectBuildOpinionRequest request);

    BaseResponse getProjectBuildOpinionInfoById(String uuId);

    BaseResponse deleteProjectBuildOpinion(String uuId);

    BaseResponse updateProjectBuildOpinion(ProjectBuildOpinionRequest request);

    BaseResponse updateApproveStatusProjectBuildOpinion(ProjectBuildOpinionRequest request);

}
