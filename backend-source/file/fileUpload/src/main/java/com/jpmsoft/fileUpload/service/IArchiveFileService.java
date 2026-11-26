package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.FormDataImpArchiveDTO;
import com.jpmsoft.fileUpload.pojo.dto.FullTextDTO;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveFile;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 13:04
 */
public interface IArchiveFileService extends IService<ArchiveFile> {

    String getAdLinkByAdId(String adId);

    /**
     * 创建归档文件的全文索引
     * 单次执行创建100个文件索引
     */
    int createArchiveFileLuceneIndex();

    /***
     * 全文检索归档文件信息
     * @param request
     * @return
     */
    BaseResponse fullSearch(FullTextDTO request);

    /**
     * 归档指定项目下归档上传的问题
     * （档案归档模块 归档上传+系统文件归档上传 不包含业务上传）
     *
     * @param faId
     * @return
     */
    BaseResponse archiveProjectFile(String faId);

    List<ArchiveFileResponse> getByAdIds(List<String> ids, String pid);


    /**
     * 通用导入归档附件
     *
     * @param archives
     * @param user
     */
    void formImpArchive(List<FormDataImpArchiveDTO> archives, LogUserInfoVo user, String root,String archive);
}
