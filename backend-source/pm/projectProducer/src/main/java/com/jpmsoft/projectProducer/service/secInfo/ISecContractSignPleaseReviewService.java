package com.jpmsoft.projectProducer.service.secInfo;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecContractSignPleaseReviewRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecContractSignPleaseReview;

public interface ISecContractSignPleaseReviewService  extends IService<SecContractSignPleaseReview> {


    BaseResponse createOrUpdateContractSignPleaseReview(SecContractSignPleaseReviewRequest request);


    BaseResponse deleteContractSignPleaseReviewById(String csprId);


    BaseResponse getContractSignPleaseReviewById(String csprId);


    BaseResponse getContractSignPleaseReviewList(SecContractSignPleaseReviewRequest request);

}
