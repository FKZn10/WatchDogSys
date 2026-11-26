package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.entity.investment.PreliminaryAudit;

public interface PreliminaryAuditService extends IService<PreliminaryAudit> {
    PreliminaryAudit getpreliminaryById(String id);

    PreliminaryAudit getpreliminaryConId(String conId);
}
