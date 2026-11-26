package com.jpmsoft.projectProducer.service.secInfo;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecControlPricePleaseReviewRequest;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlPricePleaseReview;

public interface ISecControlPricePleaseReviewService extends IService<SecControlPricePleaseReview> {


    BaseResponse createOrUpdateControlPriceReview(SecControlPricePleaseReviewRequest request);


    BaseResponse deleteControlPriceReviewById(String cpprId);


    BaseResponse getControlPriceReviewById(String cpprId);


    BaseResponse getControlPriceReviewList(SecControlPricePleaseReviewRequest request);


    SecControlPriceResponse getControlPriceReviewBysecId(String secId);


}
