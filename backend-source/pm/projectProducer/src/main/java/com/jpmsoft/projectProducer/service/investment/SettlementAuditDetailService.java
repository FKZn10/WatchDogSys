package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditDetailVo;

import java.util.List;

public interface SettlementAuditDetailService  extends IService<SettlementAuditDetail> {
    void createSettlementAuditDetail(List<SettlementAuditDetailVo> detailList, String id, Integer type);

    void deleteSettlementAuditId(String id);

    List<SettlementAuditDetail> getSettAuditDetailId(String id);
}
