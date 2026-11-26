package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureMonthDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureQueryDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonthDtl;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:06
 */
public interface IInvInfrastructureMonthDtlService extends IService<InvInfrastructureMonthDtl> {

    void createOrUpdateInfrastructureMonthDtl(List<InvInfrastructureMonthDtlDto> datas, InvInfrastructureMonth im);

    void delInfrastructureMonthDtlByImId(String imId);

    BaseResponse getInvInfrastructureMonthDtlList(InvInfrastructureQueryDto request, String uId);
}
