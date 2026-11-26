package com.jpmsoft.projectProducer.mapper.fund;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFundsDtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 资金管理-来款认领子表 Mapper
 *
 * @author tianhelin
 * @date 2025-11-24
 */
@Mapper
public interface ClaimIncomingFundsDtlMapper extends BaseMapper<ClaimIncomingFundsDtl> {
    /**
     * 获取集团已到位资金累计金额
     *
     * @param pId
     * @return
     */
    BigDecimal getGroupFundsCumulative(@Param("pId") String pId);

    /**
     * 根据pid获取来款认领明细信息用于工程专户台账
     *
     * @param pId
     * @return
     */
    List<ClaimIncomingFundsDtlDTO> getClaimIncomingFundsDtlsLedgerByPId(@Param("pId") String pId);

    /**
     * 根据pid列表获取来款认领明细信息用于工程专户台账
     *
     * @param pIds
     * @return
     */
    List<ClaimIncomingFundsDtlDTO> getClaimIncomingFundsDtlsLedgerByPIds(@Param("pIds") List<String> pIds);
}
