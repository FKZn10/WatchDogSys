package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCplTemplateDtl;

import java.util.List;

public interface IProjectCplTemplateDtlService extends IService<ProjectCplTemplateDtl> {

    void createOrUpdateProCplTemplateDtl(List<ProjectCpLibraryRequset> proCpLibrarys, String cpltId);

    void deleteProCplTemplateDtlById(String cpltId);

    BaseResponse getProCplTemplateDtlTreeByCpltId(String cpltId);

    BaseResponse getProCplTemplateDtlIdListByCpltId(String cpltId);

    /**
     * 已废弃逻辑
     *
     * @param cpltdId
     * @return
     */
    @Deprecated
    BaseResponse deleteProCplTemplateDtlByCpltdId(String cpltdId);

    /**
     * 库详情ID查询模板总数
     *
     * @param cplId
     * @return
     */
    Integer getProCplTemplateDtlCountByCplId(String cplId);



    void updateLibraryFileTypeBath(LibraryFileTypeDTO request);
}
