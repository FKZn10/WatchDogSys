package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecDocumentReleaseModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocumentRelease;

public interface ISecDocumentReleaseService extends IService<SecDocumentRelease> {

    BaseResponse createOrUpdateSecDocumentRelease(SecDocumentReleaseModel request);

    BaseResponse getSecDocumentReleaseList(SecDocumentReleaseModel request);

    BaseResponse getSecDocumentReleaseById(String sdrId);

    BaseResponse appoval2SyncDocument(String sdrId);
}
