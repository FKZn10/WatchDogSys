package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignConceptModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignConcept;


public interface IDesignConceptService extends IService<DesignConcept> {

    BaseResponse createOrUpdateDesignConcept(DesignConceptModel request);

    BaseResponse getDesignConceptList(DesignConceptModel request, String uId);

    BaseResponse getDesignConceptListNoAuth(DesignConceptModel request);

    BaseResponse getDesignConceptById(String dcId);

    BaseResponse updateDesignConceptApproveStatus(DesignConceptModel request);

}
