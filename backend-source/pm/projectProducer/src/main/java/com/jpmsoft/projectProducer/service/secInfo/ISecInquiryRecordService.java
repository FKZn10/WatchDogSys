package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecInquiryRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/22 15:48
 */
public interface ISecInquiryRecordService extends IService<SecInquiryRecord> {

    BaseResponse createOrUpdateSecInquiryRecord(SecInquiryRecordDto request);

    BaseResponse getSecInquiryRecordById(String id);

    BaseResponse getSecInquiryRecordBySecId(String secId);

    BaseResponse getSecInquiryRecordList(SecInquiryRecordDto request);

    BaseResponse getNewSecQuotationUnitBySecId(String secId);

    BaseResponse getSecInquiryRecordListClassInfo(SecInquiryRecordDto request, LogUserInfoVo user);

    BaseResponse getSecInquiryRecordListClass(SecInquiryRecordDto request, LogUserInfoVo user);

}
