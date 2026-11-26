package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanAmountDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlanAmount;

import java.util.List;
import java.util.Map;

public interface ISecProcurementPlanAmountService extends IService<SecProcurementPlanAmount> {

    void createOrUpdateSecProcurementPlanAmount(List<SecProcurementPlanAmountDto> planAmountList, String ppId);


    List<SecProcurementPlanAmountDto> getListByPpId(String ppId);

    /**
     * 通过项目id获取招采计划中所有金额
     * @param pId
     * @return
     */
    List<SecProcurementPlanAmount> getPlanAmountByPid(String pId);


    List<SecProcurementPlanAmount> getPlanAmountByBddIds(List<String> bddIdList);

    Map<String,Float> getPlanAmountMapByPid(String pId);


    List<SecProcurementPlanAmount> getInvBudgetDeclareDtlByPpid(String ppId);

}
