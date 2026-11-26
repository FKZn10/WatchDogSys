package com.jpmsoft.projectProducer.service.fund;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFundsDtl;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金管理-来款认领子表 Service接口
 *
 * @author tianhelin
 * @date 2025-11-24
 */
public interface IClaimIncomingFundsDtlService extends IService<ClaimIncomingFundsDtl> {
    /**
     * 根据来款认领主表ID批量保存或更新来款认领子表数据
     *
     * @param id
     * @param dtlList
     * @return
     */
    BaseResponse saveOrUpdateBatchByCifId(String id, List<ClaimIncomingFundsDtlDTO> dtlList);

    /**
     * 获取集团已到位资金累计金额
     *
     * @param pId
     * @return
     */
    BigDecimal getGroupFundsCumulative(String pId);
}
