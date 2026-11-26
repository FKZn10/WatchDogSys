package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractMeasurementValuationModel;
import com.jpmsoft.common.model.response.Contract.ContractMeasurementContractResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractMeasurementValuation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同计量计价低代码主表 Mapper
 */
@Mapper
public interface ContractMeasurementValuationMapper extends BaseMapper<ContractMeasurementValuation> {
    ContractMeasurementValuationModel getContractMeasurementValuationById(@Param("id") String id);

    List<ContractMeasurementValuationModel> getContractMeasurementValuationList(
            @Param("page") IPage<ContractMeasurementValuationModel> page,
            @Param("params") Map<String, Object> params);

    List<Map<String, Object>> getContractMeasurementValuationAllValues(
            @Param("selectClause") String selectClause,
            @Param("params") Map<String, Object> params);

    List<ContractMeasurementContractResponse> getFinishedConsultAndInternalAgencyContractListByPId(
            @Param("page") IPage<ContractMeasurementContractResponse> page,
            @Param("pId") String pId,
            @Param("teId") String teId,
            @Param("authSql") String authSql);

    int countAccessibleContract(@Param("conId") String conId,
                                @Param("pId") String pId,
                                @Param("teId") String teId,
                                @Param("authSql") String authSql);
}
