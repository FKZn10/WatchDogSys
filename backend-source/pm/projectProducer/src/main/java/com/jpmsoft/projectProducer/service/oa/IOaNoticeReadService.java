package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaNoticeReadRequest;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaNoticeRead;

public interface IOaNoticeReadService extends IService<OaNoticeRead> {

    BaseResponse createOaNoticeRead(OaNoticeReadRequest request);

    BaseResponse deleteOaNoticeReadByNrId(String nrId);

    BaseResponse getOaNoticeReadListByNid(String nId, String name ,String isRead);

    BaseResponse getOaNoticeReadList(OaNoticeReadRequest request);

    Boolean havingOaNoticeReadListByNid(String nId);

    BaseResponse updateOaNoticeRead(OaNoticeReadRequest request);
}
