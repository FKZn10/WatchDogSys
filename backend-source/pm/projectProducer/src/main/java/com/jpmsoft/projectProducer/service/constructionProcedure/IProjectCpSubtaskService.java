package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpSubtaskRequest;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpSubtask;


public interface IProjectCpSubtaskService extends IService<ProjectCpSubtask> {
    BaseResponse createOrUpdateProjectCpSubtask(ProjectCpSubtaskRequest request);

    BaseResponse deleteProjectCpSubtaskById(String pcsId);

    BaseResponse getProjectCpSubtaskById(String pcsId);

    BaseResponse getProjectCpSubtaskList(ProjectCpSubtaskRequest request);

}
