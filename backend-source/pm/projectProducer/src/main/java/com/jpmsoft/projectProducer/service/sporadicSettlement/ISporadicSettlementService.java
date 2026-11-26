package com.jpmsoft.projectProducer.service.sporadicSettlement;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Maintenance.SporadicSettlementRequest;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicSettlement;

public interface ISporadicSettlementService extends IService<SporadicSettlement> {
    BaseResponse setModifymrRepairState(String id, String mrRepairState);

    BaseResponse getSporadicSettlementeList(SporadicSettlementRequest sporadicSettlementRequest);

    BaseResponse getSporadicSettlementByMrId(String mrId);

    BaseResponse checkSporadicSettlement(SporadicSettlementRequest request);
}
