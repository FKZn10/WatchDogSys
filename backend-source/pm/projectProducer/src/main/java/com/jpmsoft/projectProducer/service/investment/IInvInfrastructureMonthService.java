package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureMonthDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonth;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:07
 */
public interface IInvInfrastructureMonthService extends IService<InvInfrastructureMonth> {

    BaseResponse createOrUpdateInvInfrastructureMonth(InvInfrastructureMonthDto request);

    BaseResponse getInvInfrastructureMonthById(String id);

}
