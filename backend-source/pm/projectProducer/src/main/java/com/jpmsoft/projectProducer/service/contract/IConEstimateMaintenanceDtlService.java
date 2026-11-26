package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateMaintenanceDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProApprovalDtlModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimateMaintenanceDtl;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProApprovalDtl;

import java.util.List;

public interface IConEstimateMaintenanceDtlService extends IService<ConEstimateMaintenanceDtl> {

    void createOrUpdateConEstimateMaintenanceDtl(List<ConEstimateMaintenanceDtlModel> datas, String cemid);

    void deleteConEstimateMaintenanceDtlByCemId(String cemid);

    List<ConEstimateMaintenanceDtlModel> getConEstimateMaintenanceDtlByCemId(String cemid);
}
