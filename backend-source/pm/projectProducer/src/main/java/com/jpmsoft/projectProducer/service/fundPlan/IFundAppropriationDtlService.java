package com.jpmsoft.projectProducer.service.fundPlan;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAppropriationDtl;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:30
 */
public interface IFundAppropriationDtlService extends IService<FundAppropriationDtl> {

    void createOrUpdateFundAppropriationDtl(List<FundAppropriationDtlDto> datas, String faId);

    void delFundAppropriationDtlByFaId(String faId);

    /**
     * 查询指定合同历史审批通过的拨付清单
     *
     * @param requesr
     * @return
     */
    BaseResponse getFundAppropriationDtlList(FundAppropriationDtlDto requesr);
}
