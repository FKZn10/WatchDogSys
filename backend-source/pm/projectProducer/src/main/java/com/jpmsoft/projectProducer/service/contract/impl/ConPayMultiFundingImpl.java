package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMultiFundingMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMultiFunding;
import com.jpmsoft.projectProducer.service.contract.IConPayMultiFundingService;
import org.springframework.stereotype.Service;

/**
 * 多项目合同支付项目经费号
 *
 * @author tianhelin
 * @date 2025/9/11
 */
@Service
public class ConPayMultiFundingImpl extends ServiceImpl<ConPayMultiFundingMapper, ConPayMultiFunding> implements IConPayMultiFundingService {
}
