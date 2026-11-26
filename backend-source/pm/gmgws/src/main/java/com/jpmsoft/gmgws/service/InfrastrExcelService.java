package com.jpmsoft.gmgws.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.gmgws.pojo.vo.ContractVo;
import com.jpmsoft.gmgws.pojo.vo.StatisticsVo;

import java.util.List;

public interface InfrastrExcelService  {

    BaseResponse getContractList(StatisticsVo statisticsVo);

    BaseResponse getProjectList(StatisticsVo statisticsVo, String uId);

    BaseResponse getContractLedger(StatisticsVo statisticsVo);

    BaseResponse getCapitalPayment(StatisticsVo statisticsVo);




    //查询审批通过合同
    List<ContractVo> getContractAdopt();


    //查询审批通过的合同
     List<ContractVo> getContract();

    BaseResponse getPaymentDetails(String conId);

    BaseResponse getProjectAuthenticationList(StatisticsVo statisticsVo, String uId);

    //合同统计 新
    BaseResponse getContractStatisticsList(StatisticsVo statisticsVo, String uId);
}
