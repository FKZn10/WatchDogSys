package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedDetailModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssued;

import java.util.List;

public interface IDesignIssuedService extends IService<DesignIssued> {
    void createOrUpdateDesignIssued(DesignResultsIssuedModel request, String id, String token);

    BaseResponse getDesignIssuedList(DesignIssuedModel request, String uid);

    BaseResponse getDesignIssuedById(String id, String uid);
}
