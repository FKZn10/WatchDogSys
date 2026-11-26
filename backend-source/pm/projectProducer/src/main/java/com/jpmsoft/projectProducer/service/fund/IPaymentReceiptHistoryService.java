package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.fund.PaymentReceiptHistoryDTO;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceiptHistory;

import java.util.List;

/**
 * 收款收据变化历史 服务类接口
 *
 * @author tianhelin
 * @date 2025/12/22
 */
public interface IPaymentReceiptHistoryService extends IService<PaymentReceiptHistory> {
    /**
     * 批量保存或更新收款收据变化历史
     *
     * @param prId
     * @param historyList
     * @param prIsClaim
     * @return
     */
    BaseResponse saveOrUpdateBatchByDtoList(String prId, List<PaymentReceiptHistoryDTO> historyList, String prIsClaim);

    /**
     * 根据收款收据ID获取收款收据变化历史列表
     *
     * @param prId
     * @return
     */
    List<PaymentReceiptHistoryDTO> getByPrId(String prId);
}
