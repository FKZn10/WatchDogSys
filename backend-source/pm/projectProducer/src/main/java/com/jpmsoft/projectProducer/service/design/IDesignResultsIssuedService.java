package com.jpmsoft.projectProducer.service.design;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;

import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;

import com.jpmsoft.projectProducer.pojo.entity.design.DesignResultsIssued;

public interface IDesignResultsIssuedService extends IService<DesignResultsIssued> {
    BaseResponse createOrupdateDesignResultsIssued(DesignResultsIssuedModel request, String token);

    BaseResponse getDesignResultsIssuedList(DesignResultsIssuedRequest request, String uId);

    BaseResponse getDesignResultsIssuedClassInfo(DesignResultsIssuedRequest request, String uId);

    BaseResponse getDesignResultsIssuedById(String id, String uId);

    BaseResponse getDesignResultsLssuedTree(String dirType, String uId);

    BaseResponse delDesignResultsIssuedById(DesignResultsIssuedModel request,String uId);

    BaseResponse getDesignIssuedInfoById(String id, String uid);

    BaseResponse getDownloadLogListForDesign(DesignResultsIssuedModel request,String uId);

    BaseResponse backWriteIssuedParent(DesignResultsIssuedModel request, String uid);
}
