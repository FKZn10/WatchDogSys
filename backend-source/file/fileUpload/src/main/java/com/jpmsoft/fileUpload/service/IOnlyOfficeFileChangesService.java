package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.OnlyOfficeFileChanges;

public interface IOnlyOfficeFileChangesService extends IService<OnlyOfficeFileChanges> {
    /**
     * 变更记录URL处理
     *
     * @param changesUrl
     * @param fileId
     * @param uId
     */
    void handleChanegsUrl(String changesUrl, String fileId, String uId);

    /**
     * 获取变更记录文件的下载url
     *
     * @param fileId
     * @return
     */
    String getChangesFileUrl(String fileId);
}
