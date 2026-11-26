package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignConstructionRequest;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConstruction;


public interface IDesignConstructionService extends IService<DesignConstruction> {


    BaseResponse createOrUpdateDesignConstruction(DesignConstructionRequest request,String token);

    BaseResponse getDesignConstructionList(DesignConstructionRequest request, String uId);

    BaseResponse getDesignConstructionStatisticsList(DesignConstructionRequest request, String uId);

    BaseResponse getDesignConstructionListNoAuth(DesignConstructionRequest request);

    BaseResponse deleteDesignConstructionBydcId(String dcId, String token);

    BaseResponse getDesignConstructionBydcId(String dcId);

    BaseResponse updateDesignConstructionApproveStatus(DesignConstructionRequest request);

    /**
     * 下发状态切换
     *
     * @param dcId
     * @param issued
     * @return
     */
    BaseResponse updateDesignConstructionIssued(String dcId, String issued);
}
