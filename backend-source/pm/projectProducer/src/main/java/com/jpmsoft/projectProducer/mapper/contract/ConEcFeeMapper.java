package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.Contract.ConEcFeeRequest;
import com.jpmsoft.common.model.response.Contract.ConEcFeeResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEcFee;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcFeeDesignChangeRelationVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcFeeSiteVisaRelationVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConEcFeeStandingBookVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ConEcFeeMapper extends BaseMapper<ConEcFee> {

    List<ConEcFeeResponse> getConEcFeeList(@Param("page") IPage<ConEcFeeResponse> page, @Param("params") Map<String, Object> params);

    List<ConEcFeeDesignChangeRelationVO> getMultiContractConEcRelationList(
            @Param("page") IPage<ConEcFeeDesignChangeRelationVO> page,
            @Param("type") String type,
            @Param("projectId") String projectId,
            @Param("conId") String conId,
            @Param("invalidState") Integer invalidState,
            @Param("authSql") String authSql);

    List<ConEcFeeSiteVisaRelationVO> getConSvisRelationList(
            @Param("page") IPage<ConEcFeeSiteVisaRelationVO> page,
            @Param("projectId") String projectId,
            @Param("conId") String conId,
            @Param("teId") String teId,
            @Param("invalidState") Integer invalidState,
            @Param("authSql") String authSql);

    List<ConEcFeeResponse> getConEcFeeStandingBookPage(@Param("page") IPage<ConEcFeeResponse> page, @Param("params") Map<String, Object> params);

    ConEcFeeStandingBookVo getConEcFeeStandingBook(@Param("params") Map<String, Object> params);

    List<ConEcFeeResponse> getConEcFeeClearList(@Param("page") IPage<ConEcFeeResponse> page, @Param("params") Map<String, Object> params);

    ConEcFeeResponse getConEcFeeByEcfId(@Param("ecfId") String ecfId);

    ConEcFeeResponse getConEcFeeorRecordByEcfId(@Param("ecfId") String ecfId);

    ConEcFeeResponse getConEcFeeClearById(@Param("id") String id);

    BigDecimal getConEcFeeApplyMoneySum(@Param("conId") String conId);

    BigDecimal getPreliminaryAmountSumByConId(@Param("conId") String conId);



    List<Map<String, Object>> getAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") Map<String, Object> params
    );

    List<ConEcFeeResponse> getConEcFeeListorRecord(@Param("page") IPage<ConEcFeeResponse> page, @Param("params") Map<String, Object> params);

}
