package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.userProducer.pojo.dto.FileTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.FileTemplate;

import java.util.List;

/**
 * 流程文档模板
 * create by:Aaron
 * create time: 2021-06-14
 */
public interface IFileTemplateService extends IService<FileTemplate> {

    /**
     * 获取流程文档模板信息列表
     *
     * @param request
     * @return
     */
    BaseResponse getFileTemplateList(FileTemplateDto request);


    /**
     * 新增、修改、删除、流程文档模板信息
     *
     * @param request
     * @param token
     * @return
     */
    BaseResponse CreateOrUpdateOrDeleteFileTemplateWord(FileTemplateDto request, String token);


    /**
     * 获取流程文档模板信息（树形结构） 不排除作废数据
     *
     * @return
     */
    BaseResponse getFlowFileTemplateByTree(String tName, String tCode, String token);

    /**
     * 获取流程文档模板信息（树形结构） 排除作废数据
     *
     * @return
     */
    BaseResponse getFileTempByTreeExcludeCancel(String tName, String tCode, String token);

    /**
     * 上移或者下移
     *
     * @param tId
     * @param action
     * @return
     */
    BaseResponse upOrDownFileTemplateSort(String tId, String action, String token);


    List<FileTemplateDto> getFileTemplateList(List<String> idlist, String teId);

    String getFileTmpIdByCode(String code, String token);

    FileTemplateTree getFileTemplateByCode(String tCode, String token);

    List<FileTemplateTree>  getFileTemplateByCodes(List<String> tCode, String token);

    BaseResponse getFileTemplateByParent(String id, Boolean excludeCancel, String tName, String tCode, String token);


    String getFileTemplateTableById(String id);
}
