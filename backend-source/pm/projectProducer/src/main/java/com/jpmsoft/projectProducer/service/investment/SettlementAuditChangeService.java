package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditVo;

public interface SettlementAuditChangeService extends IService<SettlementAuditChange> {
    BaseResponse changeSettAuditChange(SettlementAuditVo request);

    BaseResponse querySettAuditChangeList(SettlementAuditVo request);

    BaseResponse getSettAuditDetailChangeId(String id);
}
