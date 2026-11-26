package com.jpmsoft.userProducer.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileResponse;

import java.util.List;
import java.util.Map;

/**
 * 跨服务调用
 * 单据附件处理
 */
public interface ISysFileAnnexService {

    /**
     * 修改表单时 刷新表单附件
     *
     * @param raltionIds 长度与fileIds一致
     * @param fileIds    长度与raltionIds一致
     * @return
     */
    BaseResponse refreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId);


    /**
     * 修改表单时 刷新表单附件
     *
     * @param data
     * @return
     */
    BaseResponse refreshRelationFile(List<Map<String, String>> data, String entityId);

    /**
     * 重载封装调用refreshRelationFile
     *
     * @param fileRelationId
     * @param fileIds
     * @return
     */
    BaseResponse refreshRelationFile(String fileRelationId, String fileIds, String entityId);
    /**
     * 表单时复制(复制其他表单文件)并刷新(复制的文件和自己表单文件)表单附件
     * 项目信息变更
     * @param raltionIds
     * @param fileIds
     * @param entityId
     * @return
     */
    BaseResponse addFormCopyAndRefreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId);

    /**
     * 新增表单时 刷新表单附件
     *
     * @param ids
     * @return
     */
    BaseResponse addFormRefreshRelationFile(List<String> ids, String entityId);


    /**
     * 重载封装调用addFormRefreshRelationFile
     *
     * @param relationId
     * @return
     */
    BaseResponse addFormRefreshRelationFile(String relationId, String entityId);

    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    List<FileResponse> getFileByFileRelationId(String fileRelationId);


}
