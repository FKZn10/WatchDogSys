package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaAttendanceGroupModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAttendanceGroup;

public interface IOaAttendanceGroupService extends IService<OaAttendanceGroup> {

    BaseResponse createOrUpdateAttendanceGroup(OaAttendanceGroupModel request);

    BaseResponse createOrUpdateAttendanceGroupAndWeekday(OaAttendanceGroupModel request);

    BaseResponse getAttendanceGroupList(OaAttendanceGroupModel request);

    BaseResponse getAttendanceGroupById(String agId);
}
