package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.fund.PaymentReceiptHistoryDTO;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceiptHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收款收据变化历史 Mapper 接口
 *
 * @author tianhelin
 * @date 2025/12/22
 */
@Mapper
public interface PaymentReceiptHistoryMapper extends BaseMapper<PaymentReceiptHistory> {
    /**
     * 根据收款收据ID获取收款收据变化历史列表
     *
     * @param prId 收款收据ID
     * @return 收款收据变化历史列表
     */
    List<PaymentReceiptHistoryDTO> getByPrId(String prId);
}
