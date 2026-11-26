package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAudit;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditVo;

public interface SettlementAuditService  extends IService<SettlementAudit> {
    BaseResponse createOrUpdateSettAudit(SettlementAuditVo request, String uId);

    BaseResponse deleteSettAuditById(String id);

    BaseResponse querySettAuditList(SettlementAuditVo request);

    BaseResponse getSettAuditDetailId(String id);

    BaseResponse getConReviewAmount(String conId);

    BaseResponse querySettAuditListClassInfo(SettlementAuditVo request);

    BaseResponse changeSettAuditChange(SettlementAuditVo request);

    BaseResponse updateChangeSettAudit(String id);
}
