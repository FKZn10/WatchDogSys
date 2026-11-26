package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceSupplementModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceSupplement;

import java.text.ParseException;

public interface IOaAttendanceSupplementService extends IService<OaAttendanceSupplement> {

    BaseResponse createOrUpdateAttendanceSupplement(OaAttendanceSupplementModel request);

    BaseResponse getAttendanceSupplementList(OaAttendanceSupplementModel request);

    BaseResponse getAttendanceSupplementById(String asId);

    BaseResponse updateAttendance(OaAttendanceSupplementModel request) throws ParseException;
}
