package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMultiFundingMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractMultiFunding;
import com.jpmsoft.projectProducer.service.contract.IConContractMultiFundingService;
import org.springframework.stereotype.Service;

/**
 * 多项目合同项目经费号
 *
 * @author tianhelin
 * @date 2025/9/8
 */
@Service
public class ConContractMultiFundingImpl extends ServiceImpl<ConContractMultiFundingMapper, ConContractMultiFunding> implements IConContractMultiFundingService {
}
