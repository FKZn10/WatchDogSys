package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSelectionRecordDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecordDtl;

import java.util.List;

public interface ISecJGSelectionRecordDtlService extends IService<SecJGSelectionRecordDtl> {

    void createOrUpdateSecSelectionRecord(List<SecJGSelectionRecordDtlModel> secJGSelectionRecordDtlModels, String srId);

    void deleteAllBySrId(String srId);

    BaseResponse getJGSelectionRecordDtlCount(String exId);

    /**
     * 是否回避，是否按时参加
     * @param request
     * @return
     */
    BaseResponse updateSecJGSelectionRecordDtl(SecJGSelectionRecordDtlModel request);


    List<SecJGSelectionRecordDtlModel> getSelectionRecordDtlList(SecJGSelectionRecordDtlDto dto);

}
