package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceLeaveModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceLeave;

public interface IOaAttendanceLeaveService extends IService<OaAttendanceLeave> {


    BaseResponse createOrUpdateAttendanceLeave(OaAttendanceLeaveModel request);

    BaseResponse getAttendanceLeaveList(OaAttendanceLeaveModel request);

    BaseResponse getAttendanceLeaveById(String alId);

    BaseResponse update(OaAttendanceLeaveModel request);

    BaseResponse getDetialById(String alId);

    BaseResponse updateApproveStatus(String id);
}
