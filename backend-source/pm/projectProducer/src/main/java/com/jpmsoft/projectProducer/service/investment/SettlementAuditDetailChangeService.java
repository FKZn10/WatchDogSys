package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetailChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditDetailVo;

import java.util.List;

public interface SettlementAuditDetailChangeService  extends IService<SettlementAuditDetailChange> {
    void createSettlementAuditDetailChange(List<SettlementAuditDetailVo> detailList, String id,Integer type);

    List<SettlementAuditDetailChange> settlementAuditDetailIdList(String id);

    List<SettlementAuditDetailChange> getsettlementAuditById(String id);
}
