package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlan;
import org.springframework.stereotype.Service;

@Service
public interface ISecProcurementPlanService extends IService<SecProcurementPlan> {


    BaseResponse createOrUpdateSecProcurementPlan(SecProcurementPlanDto request);

    /**
     * 获取项目概算树
     * @param pId
     * @return
     */
    BaseResponse getSecProcurementPlanAmountTree(String pId);


    BaseResponse getSecProcurementPlanList(SecProcurementPlanDto request, String uid);


    BaseResponse getSecProcurementPlanById(String ppId);


    BaseResponse getSecProcurementPlanByPid(String pId);


}
