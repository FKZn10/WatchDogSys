package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpChangeDtl;

import java.util.List;

public interface IProjectCpChangeDtlService extends IService<ProjectCpChangeDtl> {


    void deleteProCpChangeDtlByCpId(String cpId);

    /**
     * 变更详情数据新增修改
     *
     * @param data     变更数据
     * @param cpId     变更表ID
     * @param changeId 变更前建设程序ID
     */
    void createOrUpdateProCpChangeDtl(List<ProjectCpLibraryRequset> data, String cpId, String changeId);

    List<ProjectCpChangeDtl> getAllByCpId(String cpId);

    List<String> getProjectCpChangeCplIdList(String id);

    void approve2SyncProjectCpDtl(String id, String changeId);

    BaseResponse getProCpChangeDtlTreeById(String id,String teId);
}
