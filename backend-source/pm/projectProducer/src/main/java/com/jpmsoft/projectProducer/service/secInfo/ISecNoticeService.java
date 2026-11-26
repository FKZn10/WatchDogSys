package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecNoticeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecNotice;

public interface ISecNoticeService extends IService<SecNotice> {

    BaseResponse createOrUpdateSecNotice(SecNoticeRequest request);

    BaseResponse deleteSecNoticeByNid(String nId);

    BaseResponse getSecNoticeList(SecNoticeRequest request);

    BaseResponse getSecNoticeByNid(String nId);

    BaseResponse getSecNoticeLastApprovedBySecId(String secId);
}
