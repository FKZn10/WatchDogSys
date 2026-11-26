package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignFeasibilityModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignFeasibility;


public interface IDesignFeasibilityService extends IService<DesignFeasibility> {

    BaseResponse createOrUpdateDesignFeasibility(DesignFeasibilityModel request);

    BaseResponse getDesignFeasibilityList(DesignFeasibilityModel request, String uId);

    BaseResponse getDesignFeasibilityListNoAuth(DesignFeasibilityModel request);

    BaseResponse getDesignFeasibilityById(String dfId);

    BaseResponse updateDesignFeasibilityApproveStatus(DesignFeasibilityModel request);

}
