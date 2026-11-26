package com.jpmsoft.projectProducer.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.FileServerToArchiveRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;

import java.util.List;
import java.util.Map;

/**
 * 跨服务调用
 * 单据附件处理
 */
public interface ISysFileAnnexService  extends IService<SysFileannex> {

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
     * @param relationList
     * @param entityId
     * @return
     */
    BaseResponse refreshRelationFile(List<Map<String, String>> relationList, String entityId);

    /**
     * 表单时复制(复制其他表单文件)并刷新(复制的文件和自己表单文件)表单附件
     * 项目信息变更
     *
     * @param raltionIds
     * @param fileIds
     * @param entityId
     * @return
     */
    BaseResponse addFormCopyAndRefreshRelationFile(List<String> raltionIds, List<String> fileIds, String entityId);


    /**
     * 重载封装调用refreshRelationFile
     *
     * @param fileRelationId
     * @param fileIds
     * @return
     */
    BaseResponse refreshRelationFile(String fileRelationId, String fileIds, String entityId);

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
     * 未归档文件
     *
     * @param fileRelationId
     * @return
     */
    List<FileResponse> getFileByFileRelationId(String fileRelationId);

    /**
     * 批量获取文件信息
     * 未归档文件
     *
     * @param fileRelationIds
     * @return
     */
    List<FileResponse> getFileByFileRelationIds(List<String> fileRelationIds);

    /**
     * 调用File服务 查询成果文件
     *
     * @param request
     * @return
     */
    BaseResponse getAchievementsList(AchievementsRequest request);


    BaseResponse archiveFile(FileServerToArchiveRequest request);

    /**
     * 复制文件上传记录到新的relationId
     * 仅复制记录不复制文件
     *
     * @param oldIds
     * @param newIds
     * @return
     */
    BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId);

    BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId, Boolean skipArchive);

    /**
     * 替换覆盖表单文件
     * 未归档的表单附件（不包含流程附件Sys_File）
     * 主要场景： 例：项目变更审批通过后同步文件到项目（同步后删除原文件）
     *
     * @param oldIds
     * @param newIds
     * @return
     */
    BaseResponse replaceFormFile(List<String> oldIds, List<String> newIds, String entityId);

    BaseResponse copyFileWithFileAnnex(List<SysFileannex> model, Boolean skipArchive);
}
