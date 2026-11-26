package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.ConstructionProcedure.ProjectUseLandOpinionRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectUseLandOpinion;


public interface IProjectUseLandOpinionService extends IService<ProjectUseLandOpinion> {
    /**
     * ..项目用地预审意见
     */
    BaseResponse createProjectUseLandOpinion(ProjectUseLandOpinionRequest request);

    BaseResponse getProjectUseLandOpinionList(ProjectUseLandOpinionRequest request);

    BaseResponse getProjectUseLandOpinionInfoById(String uuId);

    BaseResponse deleteProjectUseLandOpinion(String uuId);

    BaseResponse updateProjectUseLandOpinion(ProjectUseLandOpinionRequest request);

    BaseResponse updateApproveStatusProjectUseLandOpinion(ProjectUseLandOpinionRequest request);


}
