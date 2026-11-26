package com.jpmsoft.projectProducer.service.constructionProcedure;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibrary;

import java.util.List;

public interface IProjectCpLibraryService extends IService<ProjectCpLibrary> {
    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateProCpLibrary(ProjectCpLibraryRequset request);

    /**
     * ID删除
     *
     * @param cplId
     * @return
     */
    BaseResponse deleteProCpLibraryById(String cplId);

    /**
     * 获取List集合
     *
     * @return
     */
    BaseResponse getProCpLibraryTreeList(ProjectCpLibraryRequset request);

    /**
     * 获取变更详情树
     *
     * @param id
     * @return
     */
    BaseResponse getProCpChangeDtlLibraryTreeList(String id,String teId);

    /**
     * 上移或者下移
     *
     * @param cplId
     * @param action
     * @return
     */
    BaseResponse upOrDownProCpLibrary(String cplId, String action, String teId);

    List<ProjectCpLibrary> getAllList(String teId);

    /**
     * 批量修改成果文件上传附件类型
     * @return
     */
    BaseResponse updateLibraryFileTypeBath(LibraryFileTypeDTO request);
}
