package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignGeologyModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignGeology;


public interface IDesignGeologyService extends IService<DesignGeology> {
    BaseResponse createOrUpdateDesignGeology(DesignGeologyModel request);

    BaseResponse getDesignGeologyList(DesignGeologyModel request, String uId);

    BaseResponse getDesignGeologyListNoAuth(DesignGeologyModel request);

    BaseResponse getDesignGeologyById(String dgId);
}
