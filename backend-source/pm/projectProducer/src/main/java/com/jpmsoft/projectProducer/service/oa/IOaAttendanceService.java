package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendance;

public interface IOaAttendanceService extends IService<OaAttendance> {

    BaseResponse createOrUpdateAttendance(OaAttendanceModel request);

    BaseResponse getAttendanceList(OaAttendanceModel request);
}
