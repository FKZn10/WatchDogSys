package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.fileUpload.pojo.dto.FileAnnexTableAndColumnDto;
import com.jpmsoft.fileUpload.pojo.dto.FileTableColumnAndPrimaryDto;
import com.jpmsoft.fileUpload.pojo.dto.ProFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.vo.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SysFileAnnexMapper extends BaseMapper<SysFileAnnex> {

    /**
     * 批量修改文件上传状态
     *
     * @param ids
     * @return
     */
    @Deprecated
    Integer updateUploadStatusByIds(@Param("ids") List<String> ids, @Param("entityId") String entityId);

    @Deprecated
    Integer updateUploadStatusByRelationIds(@Param("ids") List<String> ids, @Param("entityId") String entityId);


    List<ArchievementsVo> getAchievementsList(@Param("params") Map<String, Object> params);

    Integer getAchievementsListCount(@Param("params") Map<String, Object> params);


    List<FileAnnexTableAndColumnDto> getFileAnnexTableAndColumnByTableCodes(@Param("tableCodes") List<String> tableCodes);

    List<ArchiveFileRecordVo> getUnArchiveFileRecordBypId(@Param("params") List<FileTableColumnAndPrimaryDto> params,
                                                          @Param("pId") String pId, @Param("filter") Map<String, Object> filter,
                                                          @Param("tCodes") List<String> tCodes, @Param("fileReplace") Integer fileReplace, @Param("secIds") List<String> secIds);

    Integer getUnArchiveFileRecordBypIdCount(@Param("params") List<FileTableColumnAndPrimaryDto> params, @Param("pId") String pId,
                                             @Param("filter") Map<String, Object> filter, @Param("tCodes") List<String> tCodes, @Param("fileReplace") Integer fileReplace, @Param("secIds") List<String> secIds);

    List<ArchiveFileRecordVo> getUnArchiveFileRecordBypIdAndTcode(@Param("pId") String pId, @Param("filter") Map<String, Object> filter,
                                                                  @Param("tCodes") List<String> tCodes, @Param("fileReplace") Integer fileReplace, @Param("secIds") List<String> secIds);

    Integer getUnArchiveFileRecordBypIdAndTcodeCount(@Param("pId") String pId, @Param("filter") Map<String, Object> filter,
                                                     @Param("tCodes") List<String> tCodes, @Param("fileReplace") Integer fileReplace, @Param("secIds") List<String> secIds);

    /**
     * 根据归档文件信息查询文件记录
     *
     * @param apId
     * @return
     */
    List<SysFileAnnex> getArchiveFileByApId(@Param("apId") String apId);


    /**
     * id集合查询数据
     *
     * @param ids
     * @return
     */
    List<SysFileAnnex> selectFileAnnexByIds(@Param("ids") List<String> ids);


    void updateFileAnnexArchiveByIds(@Param("ids") List<String> ids, @Param("archive") Integer archive);

    void updateFileAnnexArchiveByUnArchiveIds(@Param("ids") List<String> ids, @Param("archive") Integer archive);

    void updateFileAnnexArchiveByApId(@Param("apId") String apId, @Param("archive") Integer archive);


    /**
     * 获取文件数量
     *
     * @param fileRelationIds
     * @return
     */
    List<String> getFileCountByRelationIds(@Param("fileRelationIds") List<String> fileRelationIds
            , @Param("fileReplace") Integer fileReplace, @Param("fileLoation") Integer fileLoation);

    /**
     * 自动归档
     * 获取未归档文件信息(文件基本信息，流程编码、项目ID、标段ID、合同Id)
     *
     * @return
     */
    List<AutoArchiveFileVo> getUnArchiveFiles();

    /**
     * 自动归档
     * 获取未归档行政文件信息(找不到项目但又文档模板的附件，或者归档目录指定到行政目录的附件)
     *
     *
     * @param adIds
     * @return
     */
    List<AutoArchiveFileVo> getUnXzArchiveFiles(@Param("adIds") List<String> adIds);

    /**
     * 项目建议书未归档文件
     *
     * @return
     */
    List<AutoArchiveFileVo> getProProposalUnArchiveFiles();

    /**
     * 查询系统历史本地上传文件
     *
     * @param page
     * @param params
     * @return
     */
    List<ProFileAnnex> getSystemFileList(@Param("page") IPage<SysFileAnnex> page, @Param("params") Map<String, Object> params);


    List<ProFileAnnex> getFilesByRelationIds(@Param("fileLocation") Integer fileLocation,
                                             @Param("showReplace") boolean showReplace,
                                             @Param("relationIds") List<String> relationIds,
                                             @Param("showAll") boolean showAll);

    List<ProFileAnnex> getJimuFileCountByFileId(@Param("fileLocation") Integer fileLocation,
                                             @Param("showReplace") boolean showReplace,
                                             @Param("entityId") String entityId);


    @Select("select *  from  ${table} where  ${primaryKey}=#{id}")
    Map<String, Object> getData(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("id") String id);


    List<FileAnnexInfoVo> getFileAnnerxByFileId(@Param("relationIds") List<String> relationIds);


    Integer updateFileArchiveStatus(@Param("fileIds") List<String> fileIds);


    List<SysFileAnnex> getArchiveFile(@Param("adId") String adId);


    SysFileAnnex md5GetArchiveFile(@Param("jmValue") String jmValue);

    List<FileAnnexInfoWithFileVo> getFileWithoutFileAnnex();
    List<FileAnnexInfoWithFileVo> getFileWithoutFileAnnexSafe();

    List<SysFileAnnex> selectPageNoMd5(@Param("offset") int offset, @Param("pageSize") int pageSize);
}