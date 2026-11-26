package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpRpr;

public interface IProjectCpRprService extends IService<ProjectCpRpr> {

    BaseResponse approve2SyncGanttTaskState(String id);

    /**
     * 进度计划变更 判断进度计划节点是否可以移除
     * 如果有办理记录则不允许移除
     *
     * @param pId
     * @param cplId
     * @return
     */
    BaseResponse canToDeleteGanttTaskChange(String pId, String cplId);
}
