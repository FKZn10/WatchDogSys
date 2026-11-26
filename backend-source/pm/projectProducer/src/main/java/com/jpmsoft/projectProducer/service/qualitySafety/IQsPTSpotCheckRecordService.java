package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsPTSpotCheckRecordRequest;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPTSpotCheckRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPTSpotCheckRecord;

import java.util.List;

public interface IQsPTSpotCheckRecordService extends IService<QsPTSpotCheckRecord> {


    void createOrUpdateQsPTSpotCheckRecord(List<QsPTSpotCheckRecordDto> qsPTSpotCheckRecordList, String id);


    BaseResponse updateSpotCheckRectifyPic(QsPTSpotCheckRecordRequest request);

    void updateRectifyStatus(List<QsPTSpotCheckRecordDto> qsPTSpotCheckRecordList, String rectifyStatus);

    List<QsPTSpotCheckRecord> getSpotCheckRecordByQsscId(String qsscId);

    void updateRectifyStatusByIds(List<QsPTSpotCheckRecord> qsPTSpotCheckRecordList);

}
