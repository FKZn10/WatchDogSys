package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.RefershFileDto;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.Archive.ArchiveFileRequest;
import com.jpmsoft.common.model.request.ImgCompressAndRefreshStateDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.pojo.vo.AutoArchiveFileVo;
import com.jpmsoft.fileUpload.pojo.vo.FileAnnexInfoVo;
import com.jpmsoft.fileUpload.pojo.vo.FileResVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ISysFileAnnexService extends IService<SysFileAnnex> {

    /**
     * 查询系统文件（历史（来源）本地上传的文件）
     * 默认流程审批结束的业务相关的本地上传的附件
     *
     * @param query
     * @return
     */
    BaseResponse getSystemFileList(SystemFileQueryDto query, String uId);

    /**
     * 表单操作 选择上传系统历史文件(选择关联历史记录，不上传实体文件)
     * 创建临时数据（暂未复制实体文件），保存表单时再刷新附件状态
     *
     * @param request
     * @return
     */
    BaseResponse uploadSystemFile(SystemUploadDto request);

    /**
     * 保存数据 标记未未上传
     *
     * @param status           文件上传状态 标记文件是否已上传保存
     * @param sysFileAnnexPojo
     */
    void insert(SysFileAnnex sysFileAnnexPojo, int status);

    /**
     * 修改表单时
     * 刷新表单附件
     *
     * @param data
     * @return
     */
    BaseResponse refreshRelationFile(List<Map<String, String>> data, String entityId);

    /**
     * 批量刷新表单附件
     *
     * @param datas
     * @return
     */
    BaseResponse refreshRelationFileBath(List<RefershFileDto> datas);

    /**
     * 新增表单时
     * 刷新表单附件
     *
     * @param ids
     * @return
     */
    BaseResponse addFormRefreshRelationFile(List<String> ids, String entityId);


    /**
     * 新增表单数据
     * 复制其他表单文件并刷新文件上传状态
     *
     * @param data
     * @param entityId
     * @return
     */
    BaseResponse addFormCopyAndRefreshRelationFile(List<Map<String, String>> data, String entityId);

    List<SysFileAnnex> getFileListByFileIds(String fileIds);

    List<SysFileAnnex> getFileListByIds(List<String> ids);

    /**
     * 查询文件信息
     * 返回前端格式
     *
     * @param fileRelationId
     * @return
     */
    List<FileResVo> getFileByFileRelationId(String fileRelationId, Integer fileLoation, Boolean showAll);

    /**
     * 获取文件数量
     *
     * @param fileRelationIds
     * @return
     */
    BaseResponse getFileCountByRelationIds(List<String> fileRelationIds, Integer fileLoation);

    BaseResponse getJimuFileCountByFileId(String entityId, Integer fileLoation);


    /**
     * 批量
     *
     * @param fileRelationIds
     * @return
     */
    List<FileResVo> getFileByFileRelationIds(List<String> fileRelationIds, Integer fileLocation);


    List<SysFileAnnex> getFilePojoByFileRelationIds(List<String> fileRelationIds);

    BaseResponse getFileByRelationIds(List<String> relationIds, int fileLocation);

    /**
     * 成果查询
     *
     * @param request
     * @return
     */
    BaseResponse getAchievementsList(AchievementsRequest request);


    SysFileAnnex getOneFileAnnexByRelationId(String relationId);

    /**
     * 表名查询其文件上传涉及的表明和具体字段名
     *
     * @param tableCodes
     * @return
     */
    List<FileAnnexTableAndColumnDto> getFileAnnexTableAndColumnByTableCodes(List<String> tableCodes);


    /**
     * 根据项目ID 查询未归档的文件信息
     *
     * @param request
     * @return
     */
    BaseResponse getUnArchiveFileRecordByPpId(ArchiveFileRecordDto request);


    /**
     * 文件归档
     *
     * @param datas
     * @param apId
     * @return
     */
    BaseResponse archiveFile(List<ArchiveFileRequest> datas, String apId, String fileCode, Integer apState);

    /**
     * 复制文件上传记录到新的relationId
     * 复制后置为未上传状态，不需要entityId
     *
     * @param oldIds
     * @param newIds
     * @return
     */
    BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds);

    /**
     * 复制文件上传记录到新的relationId
     * 同时复制文件
     *
     * @param oldIds
     * @param newIds
     * @return
     */
    BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId);

    BaseResponse copyFileAnnex2NewRelation(List<String> oldIds, List<String> newIds, String entityId, Boolean skipArchive);

    /**
     * 替换表单文件
     * 未归档的表单附件（不包含流程附件Sys_File）
     * 主要场景： 例：项目变更审批通过后同步文件到项目（同步后删除原文件）
     *
     * @param oldIds
     * @param newIds
     * @param entityId
     * @return
     */
    BaseResponse replaceFormFile(List<String> oldIds, List<String> newIds, String entityId);

    /**
     * 自动归档
     * 获取未归档文件信息(文件基本信息，流程编码、项目ID、标段ID、合同Id)
     * 排除未上传成功、已归档、来源类型非用户上传(即选择关联历史文件的记录)等文件
     * 默认查询100条
     *
     * @return
     */
    List<AutoArchiveFileVo> getUnArchiveFiles();

    /**
     * 查询未归档的行政档案附件
     *
     * @return
     */
    List<AutoArchiveFileVo> getUnXzArchiveFiles(List<String> adIds);

    List<SysFileAnnex> getFileByEntityIds(List<String> entityIds);

    /**
     * 校验上传文件的MD5值
     * 去重
     *
     * @param md5Str
     * @param fileLocation 文件位置（归档文件和归档上传文件 1 |业务上传 0）
     * @return 存在相同true 不存在false
     */
    SysFileAnnex checkFileMd5(String md5Str, Integer fileLocation);

    /**
     * 刷新库内历史文件md5值
     */
    void refershFileMd5Code(int num);


    /**
     * 获取所有归档文件
     *
     * @return
     */
    List<SysFileAnnex> getAllArchiveFile();


    List<FileAnnexInfoVo> getFileAnnexByFileId(String fileId);

    /**
     * 删除未保存表单的文件(文件记录以及实体文件)
     *
     * @return
     */
    void deleteUnSaveFile();

    /**
     * 删除以上传OSS的本地文件
     */
    void deleteOssLocalFile();

    /**
     * 批量更新归档文件状态
     * 仅操作标记为归档路径的文件
     *
     * @param fileIds
     */
    void updateFileArchiveStatus(List<String> fileIds);

    String getFileIdsByRelationIds(List<String> relationIds);

    List<SysFileAnnex> getFileByRelationIds(List<String> relationIds);

    /**
     * 组装附件信息
     *
     * @param root       根目录
     * @param path       文件中段地址
     * @param location   文件所属根目录
     * @param fileName   上传时文件名
     * @param newName    真实文件名
     * @param fileSuffix 文件后缀
     * @param columnCode 业务表单附件字段编码
     * @param entityId   业务表单ID
     * @param relationId 业务表单附件关联Id
     * @param user       当前操作用户
     * @param temp       文档模板
     * @return
     */
    SysFileAnnex entity(String root, String path, int location, String fileName, String newName, String fileSuffix, String columnCode, String entityId, String relationId, LogUserInfoVo user, SysFileTemplate temp);


    List<SysFileAnnex> getArchiveFile(String adId);


    /**
     * 刷新图片状态
     * 给图片生成新的压缩图片
     *
     * @param files
     * @return
     */
    BaseResponse refreshImgAndCompress(@RequestBody List<ImgCompressAndRefreshStateDTO> files,String entityId);

    BaseResponse getSysfileAnnexList(FileAnnexInfoDto vo);

    ResponseEntity<byte[]> ExportSysfileAnnex(FileAnnexInfoDto dto, HttpServletResponse response);

    /**
     * 完善文件MD5值
     *
     * @return
     */
    BaseResponse completeFileMd5();

    void doBatch(List<SysFileAnnex> list, String root);

    /**
     * 校验文件是否存在以及是否符合，将符合的文件返回
     *
     * @param files
     * @return
     */
    BaseResponse validateFileExist(List<SysFileAnnex> files, String fileType);

    BaseResponse copyFileWithFileAnnex(List<SysFileAnnex> model, Boolean skipArchive);

    /**
     * 根据文件id刷新文件上传状态
     *
     * @param fileId
     * @return
     */
    BaseResponse refreshFileAnnexUploadStatusByFileId(String fileId);
}
