package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsViolationPunishmentRequest;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsViolationPunishment;

public interface IQsViolationPunishmentService extends IService<QsViolationPunishment> {


    BaseResponse createOrUpdateQsViolationPunishment(QsViolationPunishmentRequest request);

    BaseResponse getQsViolationPunishmentList(QsViolationPunishmentRequest request, String uId);

    BaseResponse getQsViolationPunishmentById(String vpId);

}
