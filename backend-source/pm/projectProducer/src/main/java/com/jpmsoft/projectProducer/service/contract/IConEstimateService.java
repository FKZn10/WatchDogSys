package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimate;

import java.util.List;

public interface IConEstimateService extends IService<ConEstimate> {


    void createOrUpdateConEstimate(List<ConEstimateModel> datas, String conId);

    void deleteAllConEstimateByConId(String conId);

    List<ConEstimateModel> getConEstimateByConIds(List<String> conIds);

    List<ConEstimateModel> getConEstimateByConId(String conId);
}
