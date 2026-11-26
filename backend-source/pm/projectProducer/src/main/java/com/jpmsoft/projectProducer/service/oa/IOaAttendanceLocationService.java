package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLocationModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLocation;

public interface IOaAttendanceLocationService extends IService<OaAttendanceLocation> {

    BaseResponse createOrUpdateOaAttendanceLocation(OaAttendanceLocationModel request);

    BaseResponse deleteOaAttendanceLocationById(String alId);

    BaseResponse getOaAttendanceLocationList(OaAttendanceLocationModel request);
}
