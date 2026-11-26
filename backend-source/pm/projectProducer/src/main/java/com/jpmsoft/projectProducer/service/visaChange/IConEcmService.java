package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.DesignChange.ConEcmRequest;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEcm;

public interface IConEcmService extends IService<ConEcm> {

    BaseResponse createOrUpdateConEcm(ConEcmRequest request);

    BaseResponse getConEcmList(ConEcmRequest request, String uId);

    BaseResponse getConEcmListNoAuth(ConEcmRequest request);

    BaseResponse getConEcmById(String ecmId);

    /**
     * 最新一条审批通过的数据
     *
     * @param dcoId
     * @return
     */
    ConEcm getLastEcmByDcoId(String dcoId);
}
