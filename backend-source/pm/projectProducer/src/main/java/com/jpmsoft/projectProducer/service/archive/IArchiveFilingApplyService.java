package com.jpmsoft.projectProducer.service.archive;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFilingApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFilingApply;


public interface IArchiveFilingApplyService extends IService<ArchiveFilingApply> {

    BaseResponse createOrUpdateArchiveFilingApply(ArchiveFilingApplyModel request);

    BaseResponse getArchiveFilingApplyList(ArchiveFilingApplyModel request, String uId);

    BaseResponse getArchiveFilingApplyById(String faId);

    BaseResponse synchronizeProject(String  id);
}
