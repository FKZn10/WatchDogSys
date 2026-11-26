package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.investment.SettlementFirstAuditChangeLogMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementFirstAuditChangeLog;
import com.jpmsoft.projectProducer.service.investment.ISettlementFirstAuditChangeLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ISettlementFirstAuditChangeLogServiceImpl extends ServiceImpl<SettlementFirstAuditChangeLogMapper, SettlementFirstAuditChangeLog> implements ISettlementFirstAuditChangeLogService {
}
