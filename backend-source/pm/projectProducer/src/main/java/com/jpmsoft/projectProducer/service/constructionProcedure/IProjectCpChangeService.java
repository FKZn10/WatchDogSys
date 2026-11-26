package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpChange;


public interface IProjectCpChangeService extends IService<ProjectCpChange> {

    BaseResponse createOrUpdateProjectCpChange(ProjectCpChangeDTO request);

    BaseResponse getProCpChangeList(ProjectCpChangeDTO request, String uId);

    /**
     * ID删除记录
     *
     * @param id
     * @return
     */
    BaseResponse deleteProCpChangeById(String id);


    BaseResponse getProjectCpChangeById(String id);


    BaseResponse getProjectCpChangeCplIdList(String id);


    BaseResponse approve2SyncProjectCpDtl(String id);

    BaseResponse getProCpChangeDtlTreeById(String id,String teId);
}
