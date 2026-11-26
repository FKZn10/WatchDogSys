package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvSettlementSecondDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementSecond;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/21 10:03
 */
public interface IInvSettlementSecondService extends IService<InvSettlementSecond> {

    BaseResponse createOrUpdateInvSettlementSecond(InvSettlementSecondDto request);

    BaseResponse getInvSettlementSecondList(InvSettlementSecondDto request, String uId);

    BaseResponse getInvSettlementSecondById(String id);
}
