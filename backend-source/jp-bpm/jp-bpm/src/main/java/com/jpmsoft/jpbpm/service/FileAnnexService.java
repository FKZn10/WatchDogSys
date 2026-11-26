package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.FileServerToArchiveRequest;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.jpbpm.pojo.entity.FileAnnex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FileAnnexService extends IService<FileAnnex> {
    Integer updateFileAnnexByrelationid(String  id,String fileids);


    /**
     * 修改表单时 刷新表单附件
     *
     * @param raltionIds 长度与fileIds一致
     * @param fileIds    长度与raltionIds一致
     * @return
     */
    BaseResponse refreshRelationFile(List<String> raltionIds, List<String> fileIds,String entityId,String token);

    /**
     * 重载封装调用refreshRelationFile
     *
     * @param fileRelationId
     * @param fileIds
     * @return
     */
    BaseResponse refreshRelationFile(String fileRelationId, String fileIds,String entityId,String token);

    /**
     * 新增表单时 刷新表单附件
     *
     * @param ids
     * @return
     */
    BaseResponse addFormRefreshRelationFile(List<String> ids,String token);

    /**
     * 重载封装调用addFormRefreshRelationFile
     *
     * @param relationId
     * @return
     */
    BaseResponse addFormRefreshRelationFile(String relationId,String token);


    /**
     * 获取文件信息
     *
     * @param fileRelationId
     * @return
     */
    List<FileResponse> getFileByFileRelationId(String fileRelationId,String token);


    /**
     * 调用File服务 查询成果文件
     *
     * @param request
     * @return
     */
    BaseResponse getAchievementsList(AchievementsRequest request,String token);

    List<FileResponse>  getFileByFileRelationIds(List<String> fileRelationIds,String token);
    BaseResponse archiveFile(FileServerToArchiveRequest request,String token);


    /**
     * 流程结束后更新合同编码
     *
     * @param id
     * @return
     */
    String  generateContractCode(String id,String token);
}
