package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConEstimateMaintenanceRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimate;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimateMaintenance;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IConEstimateMaintenanceService extends IService<ConEstimateMaintenance> {

    BaseResponse createOrUpdateConEstimateMaintenance(ConEstimateMaintenanceRequest request);

    BaseResponse getConEstimateMaintenanceList(ConEstimateMaintenanceRequest request, String uId);

    BaseResponse getConEstimateMaintenanceById(String cemId);

    BaseResponse getConEstimateMaintenanceByConIdAndSecId(String ConId,String SecId);

    BaseResponse getConEstimateMaintenanceByIds(String pId,String type);

    //审批节点更新---流程为7的时候像合同概算表中插入数据
    @Transactional(rollbackFor = Exception.class)
    BaseResponse updateConEstimate(ConEstimateMaintenanceRequest request);


    /**
     * 根据项目ID获取该项目下概算/估算/匡算的总额
     */
    BaseResponse getConEstimateIsOver(String pId,String secId);

    BaseResponse getInvestmentTableByPId(String pId);

    BaseResponse getInvestmentTableSecondByPId(String pId);

}
