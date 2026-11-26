package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeResultRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeResult;

public interface IPeResultService extends IService<PeResult> {

    void insertPeResult(String peId);

    BaseResponse signPeResult(PeResultRequest request);

    BaseResponse getPeResultList(PeResultRequest request, String uId);

    BaseResponse getPeResultByRid(PeResultRequest request);
}
