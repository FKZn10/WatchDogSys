package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.ExtractJGAgencyExpertDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGAgencyExpertSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGAgencySelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

public interface ISecJGAgencySelectionRecordService extends IService<SecJGAgencySelectionRecord> {


    BaseResponse createOrUpdateSecJGAgencySelectionRecord(SecJGAgencySelectionRecordModel request, LogUserInfoVo userInfoByToken);


    BaseResponse getSecJGAgencySelectionRecordList(SecJGAgencySelectionRecordModel request, String uid);


    BaseResponse getJGAgencySectionDocumentCount(SecJGAgencySelectionRecordModel request);


    BaseResponse getSecJGAgencySelectionRecordById(String srId);


    BaseResponse deleteSecJGAgencySelectionRecordById(String srId);


    BaseResponse checkJGAgencyExPeople(ExtractJGAgencyExpertDto request, String teId);


    BaseResponse createOrUpdateJGAgencyExpertExtract(SecJGAgencySelectionRecordModel request, LogUserInfoVo userInfoByToken);


    BaseResponse getSecJGAgencyExpertExtractById(String srId);


    BaseResponse getSecJGAgencyExpertExtractByEsId(String esId);


    BaseResponse getSecJGAgencySelectionRecordDtlModelById(String srId);


    BaseResponse getSecJGAgencyExpertExtractList(SecJGAgencyExpertSelectDto request, String uid);

    BaseResponse cancelJGAgencyExtractCancelSelect(String srId);

    BaseResponse getSecJGAgencyValueById(String srId);
}
