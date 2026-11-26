package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGContactOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface IBGContactOrderService extends IService<BGContactOrder> {

    BigDecimal getLJBGMoney(ProBGRequest request);

    String SetContactOrderBL(ProBGRequest request);

    Page<ProBGDTO> getBGContactOrderListByType(ProBGRequest request);

    /**
     * 获取指定联系事项被使用的金额比例和
     * @param contactItemId
     * @return
     */
    BigDecimal getSumOfContactItemRateByContactItemId(String contactItemId);

    /**
     * 根据合同id获取合同累计有效变更金额
     *
     * @param contractIdList
     * @return key为合同id，value为金额
     */
    Map<String, BigDecimal> getContractChangeAmountSum(List<String> contractIdList);
}
