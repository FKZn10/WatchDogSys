package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeFormDetail;

import java.util.List;

public interface IPeFormDetailService extends IService<PeFormDetail> {

    BaseResponse createOrUpdatePeFormDetail(PeFormDetailRequest request);

    BaseResponse getPeFormDetailList(PeFormDetailRequest request, String uId);

    BaseResponse deletePeFormDetailByFdId(String fdId);

    void deletePeFormToDeleteDetail(String fId);

    List<PeFormDetailRequest> getPeFormDetailCascadeInfo(String fId);
}
