package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.SysFileDownload;

import java.util.List;

public interface ISysFileDownloadService extends IService<SysFileDownload> {

    void insertSysFileDownload(SysFileDownload sysFileDownloadPojo);


    SysFileDownload getSysFileDownload(String fileIds);

    List<SysFileDownload> getSysFileDownloadBath(List<String> fileIds);

    void deleteFileDownLoadByFileId(String fileId);
}
