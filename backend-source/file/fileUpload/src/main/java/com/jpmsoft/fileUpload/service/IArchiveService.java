package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.Archive;
import com.jpmsoft.fileUpload.pojo.vo.AutoArchiveDirectoryVo;

import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 13:05
 */
public interface IArchiveService extends IService<Archive> {

    /**
     * 自动归档
     * 获取所有的目录（ID对应的文档编码）
     *
     * @return
     */
    List<AutoArchiveDirectoryVo> getAutoArchiveDirectory();

    /**
     * 自动归档
     * 获取所有的目录（ID对应的文档编码）
     * 行政归档目录
     *
     * @return
     */
    List<AutoArchiveDirectoryVo> getAutoXzArchiveDirectory();

    /**
     * 自动归档
     *
     * @return
     */
    BaseResponse autoArchive();

    /**
     * 行政档案附件归档
     *
     * @return
     */
    BaseResponse autoXzArchive();

    /**
     * 自动归档积木报表PDF
     *
     * @return
     */
    BaseResponse autoArchiveJimuReport(int size);

    /**
     * 自动归档补偿
     * 校验文件是否拷贝过去
     *
     * @return
     */
    BaseResponse autoSyncArchive();

    void refreshApStateBath(List<String> apIds, Integer apState);

    /**
     * 在自动归档后单独处理project_cp_rpr里的文件的文件编码
     *
     * @return
     */
    BaseResponse autoArchiveProCpRprFileInputCode();
}
