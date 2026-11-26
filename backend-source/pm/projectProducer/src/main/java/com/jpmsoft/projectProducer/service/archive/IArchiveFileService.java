package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveStatisticsModel;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFile;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;


public interface IArchiveFileService extends IService<ArchiveFile> {

    void createOrUpdateArchiveFile(List<ArchiveFileRequest> datas, String apId, Integer apState
            , String fileCodes, String uId, String uName, String teId);

    void deleteAllArchiveFileByApId(String apId);

    List<ArchiveFileResponse> getArchiveFileByApIds(List<String> apIds);

    BaseResponse getArchiveFileStatistics(ArchiveStatisticsModel request, String uId);

    /**
     * 按目录授权和附件创建人的同部门关系分页查询归档文件。
     *
     * @param request 文件筛选及分页条件
     * @param user    当前登录用户
     * @return 当前页文件及总数
     */
    BaseResponse getArchiveFileStatisticsWZ(ArchiveStatisticsModel request, LogUserInfoVo user);

    /**
     * 更新归档文件状态
     *
     * @param id
     * @param state
     * @return
     */
    BaseResponse updateArchiveFileState(String id, Integer state);

    BaseResponse deleteArchiveFileById(ArchiveStatisticsModel request);
}
