package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeResultAppealRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeResultAppeal;

public interface IPeResultAppealService extends IService<PeResultAppeal> {

    BaseResponse createOrUpdateResultAppeal(PeResultAppealRequest request);

    BaseResponse getResultAppealList(PeResultAppealRequest request, String uId);

    BaseResponse deleteResultAppealById(PeResultAppealRequest request);

}
