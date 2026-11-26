package com.jpmsoft.projectProducer.service.secInfo;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaInquiryDocDTO;

public interface ISecWorkSchemaInquiryDocService {


    BaseResponse createOrUpdateSecWorkSchemaInquiryDoc(SecWorkSchemaInquiryDocDTO request);

    BaseResponse getSecWorkSchemaInquiryDocList(SecWorkSchemaInquiryDocDTO request);

    BaseResponse getSecWorkSchemaInquiryDocByWsidId(String wsidId);

    BaseResponse getSecWorkSchemaInquiryDocBySecId(String secId);

}
