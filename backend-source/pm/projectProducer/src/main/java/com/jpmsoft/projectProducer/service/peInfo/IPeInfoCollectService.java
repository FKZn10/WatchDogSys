package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeInfoCollectRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollect;

public interface IPeInfoCollectService extends IService<PeInfoCollect> {

    BaseResponse createOrUpdatePeInfoCollect(PeInfoCollectRequest request);

    BaseResponse deletePeInfoCollectByPecId(String pecId);

    BaseResponse updatePeInfoCollectFlowState(PeInfoCollectRequest request);

    BaseResponse getPeInfoCollectList(PeInfoCollectRequest request);

    BaseResponse getPeInfoCollectByPecId(String pecId);

    BaseResponse getPeInfoCollectExportByPecId(String pecId, String uId);

    /**
     * 退回发起人时
     * 删除汇总清单
     *
     * @param pecId
     * @return
     */
    BaseResponse deletePeInfoCollectDtlWhenBackByPecId(String pecId);
}
