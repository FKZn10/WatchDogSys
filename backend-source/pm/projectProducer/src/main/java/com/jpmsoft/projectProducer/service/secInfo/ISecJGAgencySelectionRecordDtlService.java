package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGAgencySelectionRecordModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGAgencySelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGAgencySelectionRecordDtl;

import java.util.List;

public interface ISecJGAgencySelectionRecordDtlService extends IService<SecJGAgencySelectionRecordDtl> {

    void createOrUpdateSecSelectionRecord(List<SecJGAgencySelectionRecordDtlModel> secJGSelectionRecordDtlModels, String id);

    BaseResponse getJGAgencySelectionRecordDtlCount(String exId);


    BaseResponse updateSecJGAgencySelectionRecordDtl(SecJGAgencySelectionRecordDtlModel request);


    void deleteAllBySrId(String srId);

    List<SecJGAgencySelectionRecordDtlModel> getSelectionRecordDtlList(SecJGAgencySelectionRecordDtlDto dto);

}
