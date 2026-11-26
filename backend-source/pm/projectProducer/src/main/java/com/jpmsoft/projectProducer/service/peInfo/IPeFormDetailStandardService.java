package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailStandardRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeFormDetailStandard;

import java.util.List;

public interface IPeFormDetailStandardService extends IService<PeFormDetailStandard> {

    BaseResponse createOrUpdatePeFormStandard(PeFormDetailStandardRequest request);

    BaseResponse getPeFormStandardList(PeFormDetailStandardRequest request);

    BaseResponse deletePeFormStandardById(String sId);

    void deleteDetailToDeleteStandard(String fdId);

    void deletePeFormToDeleteStandard(List<String> fdIds);

    List<PeFormDetailStandardRequest> getPeFormDetailStandardByFid(String fId);
}
