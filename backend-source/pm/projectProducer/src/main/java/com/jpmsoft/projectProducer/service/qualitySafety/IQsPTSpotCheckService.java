package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsPTSpotCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPTSpotCheck;

public interface IQsPTSpotCheckService extends IService<QsPTSpotCheck> {


    BaseResponse createOrUpdateQsPTSpotCheck(QsPTSpotCheckDto request);

    BaseResponse getQsPTSpotCheckList(QsPTSpotCheckDto request, String uid);

    BaseResponse getQsPTSpotCheckListNoAuth(QsPTSpotCheckDto request);

    BaseResponse getQsPTSpotCheckById(String id);

    BaseResponse approvalData(QsPTSpotCheckDto request);

}
