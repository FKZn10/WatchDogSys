package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Archive.ArchiveRequest;
import com.jpmsoft.projectProducer.pojo.entity.archive.Archive;

import java.util.List;


public interface IArchiveService extends IService<Archive> {

    BaseResponse createOrUpdateArchive(ArchiveRequest request);

    BaseResponse deleteArchiveByApId(String apId);

    BaseResponse getArchiveList(ArchiveRequest request, String uId);

    /**
     * 目录是否有归档信息
     *
     * @param adIds
     * @return
     */
    boolean directoryHavingArchiveFile(List<String> adIds);
}
