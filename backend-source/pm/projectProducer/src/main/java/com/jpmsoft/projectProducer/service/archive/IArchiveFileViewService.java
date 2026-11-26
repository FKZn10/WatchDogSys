package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFileViewModel;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFileView;


public interface IArchiveFileViewService extends IService<ArchiveFileView> {

    BaseResponse createOrUpdateArchiveFileView(ArchiveFileViewModel request);

    BaseResponse getArchiveFileViewList(ArchiveFileViewModel request);

    void delArchiveFileViewByApId(String apId);
}
