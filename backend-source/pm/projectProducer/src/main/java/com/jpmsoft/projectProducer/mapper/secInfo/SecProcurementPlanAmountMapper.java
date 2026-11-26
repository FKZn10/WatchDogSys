package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanAmountDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlanAmount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SecProcurementPlanAmountMapper extends BaseMapper<SecProcurementPlanAmount> {


    List<SecProcurementPlanAmountDto> getListByPpId(@Param("ppId") String ppId);


    List<SecProcurementPlanAmount> getPlanAmountByPid(@Param("pId") String pId);

    List<SecProcurementPlanAmount> getInvBudgetDeclareDtlByPpid(@Param("ppId")  String ppId);

    List<SecProcurementPlanAmount> getInvBudgetDeclareDtlByBddIdList(@Param("bddIdList")  List<String> bddIdList);

}
