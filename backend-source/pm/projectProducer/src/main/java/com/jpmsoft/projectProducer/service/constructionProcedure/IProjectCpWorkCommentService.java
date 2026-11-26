package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpWorkCommentDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkComment;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 11:58
 */
public interface IProjectCpWorkCommentService extends IService<ProjectCpWorkComment> {

    BaseResponse createOrUpdateWorkComment(ProjectCpWorkCommentDTO request);

    BaseResponse getWorkCommentList(ProjectCpWorkCommentDTO request);

    BaseResponse deleteById(String id);

    void deleteByCpwrId(String cpwrId);
}
