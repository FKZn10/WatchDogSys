package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractPricePaymentInfoDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ContractPricePaymentInfoMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPricePaymentInfo;
import com.jpmsoft.projectProducer.service.contract.IContractPricePaymentInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 合同价款及支付信息Service实现类
 */
@Service
public class ContractPricePaymentInfoImpl extends ServiceImpl<ContractPricePaymentInfoMapper, ContractPricePaymentInfo> implements IContractPricePaymentInfoService {
    /**
     * 新增或更新合同价款及支付信息
     *
     * @param contractPricePaymentInfos
     * @param conId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateContractPricePaymentInfo(List<ContractPricePaymentInfoDTO> contractPricePaymentInfos, String conId) {
        // 先删除全部
        this.remove(Wrappers.<ContractPricePaymentInfo>lambdaQuery().eq(ContractPricePaymentInfo::getConId, conId));
        // 再构建新增
        if (ValidateUtil.isNotEmpty(contractPricePaymentInfos)) {
            List<ContractPricePaymentInfo> paymentInfos = new ArrayList<>();
            contractPricePaymentInfos.forEach(contractPricePaymentInfo -> {
                ContractPricePaymentInfo entity = new ContractPricePaymentInfo();
                BeanUtils.copyProperties(contractPricePaymentInfo, entity);
                entity.setId(GenerateUUID.getUUID());
                entity.setConId(conId);
                paymentInfos.add(entity);
            });
            this.saveBatch(paymentInfos);
        }
    }

    /**
     * 根据合同id列表获取合同价款及支付信息
     *
     * @param conIds
     * @return
     */
    @Override
    public List<ContractPricePaymentInfoDTO> getPaymentInfoList(List<String> conIds) {
        if (ValidateUtil.isEmpty(conIds)) {
            return Collections.emptyList();
        }
        List<ContractPricePaymentInfo> contractPayments = this.list(Wrappers.<ContractPricePaymentInfo>lambdaQuery().in(ContractPricePaymentInfo::getConId, conIds));
        return BeanUtils.copyList(contractPayments, ContractPricePaymentInfoDTO.class);
    }
}
