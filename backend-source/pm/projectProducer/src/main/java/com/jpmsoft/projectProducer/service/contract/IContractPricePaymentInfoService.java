package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractPricePaymentInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPricePaymentInfo;

import java.util.List;

/**
 * 合同价款及支付信息Service
 */
public interface IContractPricePaymentInfoService extends IService<ContractPricePaymentInfo> {
    /**
     * 新增或更新合同价款及支付信息
     *
     * @param contractPricePaymentInfos
     * @param conId
     */
    void createOrUpdateContractPricePaymentInfo(List<ContractPricePaymentInfoDTO> contractPricePaymentInfos, String conId);

    /**
     * 根据合同id列表获取合同价款及支付信息
     *
     * @param conIds
     * @return
     */
    List<ContractPricePaymentInfoDTO> getPaymentInfoList(List<String> conIds);
}
